package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryTeamSQL implements RepositoryTeam {
    @Override
    public Team create(Team team) {
        String SQL_INSERT = "INSERT INTO team (name) VALUES (?)";
        String SQL_GET_ID_INSERTING = "SELECT LAST_INSERT_ID()";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/tuto?serverTimezone=UTC", "root", "root");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {

            preparedStatement.setString(1, team.getName());

            preparedStatement.executeUpdate();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_GET_ID_INSERTING);

            // rows affected
            rs.next();
            int id = rs.getInt(1);
            team.setId(String.valueOf(id));

            for (Person person: team.getList()) {
                bePartOf(person, team);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return team;
    }

    public void bePartOf(Person person, Team team) {
        String SQL_INSERT = "INSERT INTO bepartof (idPerson, idTeam) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/tuto?serverTimezone=UTC", "root", "root");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {

            preparedStatement.setString(1, person.getId());
            preparedStatement.setString(2, team.getId());

            preparedStatement.executeUpdate();

            conn.createStatement();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Team> getAll() {
        List<Team> allTeam = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/tuto?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM team");
            while (rs.next()) {
                Team team = Team.builder().id(rs.getString(1)).name(rs.getString(2)).build();
                team.setList(getPersonBePartOfTeam(team));
                allTeam.add(team);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTeam;
    }

    @Override
    public Optional<Team> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean existsByName(String name) {
        List<String> allTeamName = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/tuto?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM team");
            while (rs.next()) {
                allTeamName.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTeamName.contains(name);
    }

    public List<Person> getPersonBePartOfTeam(Team team) {
        List<Person> list = new ArrayList<>();
        String SQL_SELECT = "SELECT person.* FROM bepartof, person WHERE person.id = bepartof.idPerson AND bepartof.idTeam = ?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/tuto?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

            preparedStatement.setString(1, team.getId());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(Person.builder().id(rs.getString(1))
                        .firstName(rs.getString(2))
                        .lastName(rs.getString(3))
                        .age(rs.getInt(4))
                        .build());
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
