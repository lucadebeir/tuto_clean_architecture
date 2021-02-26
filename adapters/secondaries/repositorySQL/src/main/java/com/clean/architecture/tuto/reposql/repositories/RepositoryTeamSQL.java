package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.clean.architecture.tuto.reposql.config.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryTeamSQL implements RepositoryTeam {

    @Override
    public Team create(Team team) throws SQLException {
        String SQL_INSERT = "INSERT INTO team (name) VALUES (?)";
        String SQL_GET_ID_INSERTING = "SELECT LAST_INSERT_ID()";

        Connection connection = Config.SingletonSQL.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        preparedStatement.setString(1, team.getName());
        preparedStatement.executeUpdate();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL_GET_ID_INSERTING);

        // rows affected
        rs.next();
        int id = rs.getInt(1);
        team.setId(String.valueOf(id));

        for (Person person: team.getList()) {
            bePartOf(person, team);
        }

        return team;
    }

    public void bePartOf(Person person, Team team) throws SQLException {
        String SQL_INSERT = "INSERT INTO bepartof (idPerson, idTeam) VALUES (?, ?)";

        Connection connection = Config.SingletonSQL.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        preparedStatement.setString(1, person.getId());
        preparedStatement.setString(2, team.getId());
        preparedStatement.executeUpdate();

        connection.createStatement();
    }

    @Override
    public List<Team> getAll() throws SQLException {
        List<Team> allTeam = new ArrayList<>();
        Connection connection = Config.SingletonSQL.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM team");
        while (rs.next()) {
            Team team = Team.builder().id(rs.getString(1)).name(rs.getString(2)).build();
            team.setList(getPersonBePartOfTeam(team));
            allTeam.add(team);
        }

        return allTeam;
    }

    @Override
    public Optional<Team> findById(String id) throws SQLException {
        String SQL_SELECT_TEAM = "SELECT * FROM team WHERE id = ?";

        Connection connection = Config.SingletonSQL.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TEAM);

        preparedStatement.setString(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        Team t = null;

        while (rs.next()) {
            t = Team.builder().id(id).name(rs.getString("name")).build();
            t.setList(getPersonBePartOfTeam(t));
        }
        rs.close();

        return Optional.of(t);
    }

    @Override
    public boolean existsByName(String name) throws SQLException {
        List<String> allTeamName = new ArrayList<>();
        Connection connection = Config.SingletonSQL.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM team");
        while (rs.next()) {
            allTeamName.add(rs.getString(1));
        }
        return allTeamName.contains(name);
    }

    public List<Person> getPersonBePartOfTeam(Team team) throws SQLException {
        List<Person> list = new ArrayList<>();
        String SQL_SELECT = "SELECT person.* FROM bepartof, person WHERE person.id = bepartof.idPerson AND bepartof.idTeam = ?";

        Connection connection = Config.SingletonSQL.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT);
        preparedStatement.setString(1, team.getId());

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(Person.builder().id(rs.getString(1))
                    .firstName(rs.getString(2))
                    .lastName(rs.getString(3))
                    .age(rs.getInt(4))
                    .build());
        }

        return list;
    }
}
