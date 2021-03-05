package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;

import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryTeamSQL extends AbstractRepositorySQL implements RepositoryTeam {

    int idTeam1;
    int idTeam2;
    int idPerson1;
    int idPerson2;

    public RepositoryTeamSQL() {
        super();
    }

    public void createDataSet() throws SQLException {
        idTeam1 = insertOneRequete("INSERT INTO team (name) VALUES ('Arsenal')");
        idPerson1 = insertOneRequete("INSERT INTO person (lastname, firstname, age) VALUES ('Romain', 'Chief', 42)");
        idTeam2 = insertOneRequete("INSERT INTO team (name) VALUES ('Manchester United')");
        idPerson2 = insertOneRequete("INSERT INTO person (lastname, firstname, age) VALUES ('Vincent', 'Olivier', 60)");

        connection.createStatement()
                .execute("INSERT INTO bepartof (idPerson, idTeam) VALUES (" + idPerson1 + ", " + idTeam1 + ")");

        connection.createStatement()
                .execute("INSERT INTO bepartof (idPerson, idTeam) VALUES (" + idPerson2 + ", " + idTeam2 + ")");
    }

    public int insertOneRequete(String sql) throws SQLException {
        connection.createStatement()
                .execute(sql);
        ResultSet rs = connection.createStatement()
                .executeQuery("SELECT LAST_INSERT_ID()");
        rs.next();
        return rs.getInt(1);
    }

    public void removeDataSet() throws SQLException {
        connection.createStatement()
                .executeUpdate("DELETE FROM team WHERE id IN (" + idTeam1 + ", " + idTeam2 + ") ");
        connection.createStatement()
                .executeUpdate("DELETE FROM bepartof WHERE idTeam IN (" + idTeam1 + ", " + idTeam2 + ") ");
        connection.createStatement()
                .executeUpdate("DELETE FROM person WHERE id IN (" + idPerson1 + ", " + idPerson2 + ") ");
    }


    @Override
    public Team create(Team team) throws SQLException {
        String SQL_INSERT = "INSERT INTO team (name) VALUES (?)";
        String SQL_GET_ID_INSERTING = "SELECT LAST_INSERT_ID()";

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        preparedStatement.setString(1, team.getName());
        preparedStatement.executeUpdate();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL_GET_ID_INSERTING);

        // rows affected
        rs.next();
        int id = rs.getInt(1);
        team.setId(String.valueOf(id));

        for (Person person : team.getList()) {
            bePartOf(person, team);
        }

        return team;
    }

    public void bePartOf(Person person, Team team) throws SQLException {
        String SQL_INSERT = "INSERT INTO bepartof (idPerson, idTeam) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        preparedStatement.setString(1, person.getId());
        preparedStatement.setString(2, team.getId());
        preparedStatement.executeUpdate();

        connection.createStatement();
    }

    @Override
    public List<Team> getAll() throws SQLException {
        List<Team> allTeam = new ArrayList<>();
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

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TEAM);

        preparedStatement.setString(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        Team t = null;

        while (rs.next()) {
            t = Team.builder().id(id).name(rs.getString("name")).build();
            t.setList(getPersonBePartOfTeam(t));
        }
        rs.close();

        return Optional.ofNullable(t);
    }

    @Override
    public boolean existsByName(String name) throws SQLException {
        List<String> allTeamName = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM team");
        while (rs.next()) {
            allTeamName.add(rs.getString(1));
        }
        return allTeamName.contains(name);
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Team update(Team team) throws UnknownHostException, SQLException {
        return null;
    }

    public List<Person> getPersonBePartOfTeam(Team team) throws SQLException {
        List<Person> list = new ArrayList<>();
        String SQL_SELECT = "SELECT person.* FROM bepartof, person WHERE person.id = bepartof.idPerson AND bepartof.idTeam = ?";

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
