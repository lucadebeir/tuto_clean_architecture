package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.clean.architecture.tuto.core.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryTeamSQL extends AbstractRepositorySQL implements RepositoryTeam {

    String uuidTeam1;
    String uuidTeam2;
    String uuidPerson1;
    String uuidPerson2;


    public RepositoryTeamSQL() {
        super();
    }

    public void createDataSet() throws SQLException, UnsupportedEncodingException {
        uuidTeam1 = Utils.getGuidFromByteArray(UUID.randomUUID().toString().getBytes("UTF-8"));
        uuidTeam2 = Utils.getGuidFromByteArray(UUID.randomUUID().toString().getBytes("UTF-8"));
        uuidPerson1 = Utils.getGuidFromByteArray(UUID.randomUUID().toString().getBytes("UTF-8"));
        uuidPerson2 = Utils.getGuidFromByteArray(UUID.randomUUID().toString().getBytes("UTF-8"));
        insertOneRequete("INSERT INTO team (uuid, name) VALUES (" + uuidTeam1 + ", 'Arsenal')");
        insertOneRequete("INSERT INTO person (lastname, firstname, age) VALUES (" + uuidPerson1 + ", 'Romain', 'Chief', 42)");
        insertOneRequete("INSERT INTO team (name) VALUES (" + uuidTeam2 + ", 'Manchester United')");
        insertOneRequete("INSERT INTO person (lastname, firstname, age) VALUES (" + uuidPerson2 + ", 'Vincent', 'Olivier', 60)");

        connection.createStatement()
                .execute("INSERT INTO bepartof (idPerson, idTeam) VALUES (" + uuidPerson1 + ", " + uuidTeam1 + ")");

        connection.createStatement()
                .execute("INSERT INTO bepartof (idPerson, idTeam) VALUES (" + uuidPerson2 + ", " + uuidTeam2 + ")");
    }

    public void insertOneRequete(String sql) throws SQLException {
        connection.createStatement()
                .execute(sql);
    }

    public void removeDataSet() throws SQLException {
        connection.createStatement()
                .executeUpdate("DELETE FROM team WHERE uuid IN (" + uuidTeam1 + ", " + uuidTeam2 + ") ");
        connection.createStatement()
                .executeUpdate("DELETE FROM bepartof WHERE uuidTeam IN (" + uuidTeam1 + ", " + uuidTeam2 + ") ");
        connection.createStatement()
                .executeUpdate("DELETE FROM person WHERE uuid IN (" + uuidPerson1 + ", " + uuidPerson2 + ") ");
    }


    @Override
    public Team create(Team team) throws SQLException {
        team.setUuid(Utils.getGuidFromByteArray(UUID.randomUUID().toString().getBytes()));

        String SQL_INSERT = "INSERT INTO team (uuid, name) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        preparedStatement.setString(1, team.getUuid());
        preparedStatement.setString(2, team.getName());
        preparedStatement.executeUpdate();

        for (Person person : team.getList()) {
            bePartOf(person, team);
        }

        return team;
    }

    public void bePartOf(Person person, Team team) throws SQLException {
        String SQL_INSERT = "INSERT INTO bepartof (uuidPerson, uuidTeam) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        preparedStatement.setString(1, person.getUuid());
        preparedStatement.setString(2, team.getUuid());
        preparedStatement.executeUpdate();

        connection.createStatement();
    }

    @Override
    public List<Team> getAll() throws SQLException {
        List<Team> allTeam = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM team");
        while (rs.next()) {
            Team team = Team.builder()
                    .uuid(rs.getString(1))
                    .name(rs.getString(2))
                    .build();
            team.setList(getPersonBePartOfTeam(team));
            allTeam.add(team);
        }

        return allTeam;
    }

    @Override
    public Optional<Team> findByUuid(String uuid) throws SQLException {
        String SQL_SELECT_TEAM = "SELECT * FROM team WHERE uuid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TEAM);

        preparedStatement.setString(1, uuid);

        ResultSet rs = preparedStatement.executeQuery();
        Team t = null;

        while (rs.next()) {
            t = Team.builder().uuid(uuid).name(rs.getString("name")).build();
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
    public void deleteByUuid(String uuid) {

    }

    @Override
    public Team update(Team team) throws UnknownHostException, SQLException {
        return null;
    }

    public List<Person> getPersonBePartOfTeam(Team team) throws SQLException {
        List<Person> list = new ArrayList<>();
        String SQL_SELECT = "SELECT person.* FROM bepartof, person WHERE person.uuid = bepartof.uuidPerson AND bepartof.uuidTeam = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT);
        preparedStatement.setString(1, team.getUuid());

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(Person.builder().uuid(rs.getString(1))
                    .firstName(rs.getString(2))
                    .lastName(rs.getString(3))
                    .age(rs.getInt(4))
                    .build());
        }

        return list;
    }
}
