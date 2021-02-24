package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryPersonSQL implements RepositoryPerson {

    public Person create(Person person) {
        String SQL_INSERT = "INSERT INTO person (lastname, firstname, age) VALUES (?,?,?)";
        String SQL_GET_ID_INSERTING = "SELECT LAST_INSERT_ID()";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/tuto?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {

            preparedStatement.setString(1, person.getLastName());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setInt(3, person.getAge());

            preparedStatement.executeUpdate();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_GET_ID_INSERTING);

            // rows affected
            rs.next();
            int id = rs.getInt(1);
            person.setId(String.valueOf(id));

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // person.setId();
        return person;
    }

    @Override
    public List<Person> getAll() {
        List<Person> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/tuto?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM person");
            while (rs.next()) {
                list.add(new Person(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
                ));
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Person> findById(String id) {

        String SQL_SELECT_PERSON = "SELECT * FROM person WHERE id = ?";
        Person p = null;
        
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/tuto?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_PERSON)) {

            preparedStatement.setString(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            p = new Person(id,
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getInt("age"));

            rs.close();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.of(p);

    }

}
