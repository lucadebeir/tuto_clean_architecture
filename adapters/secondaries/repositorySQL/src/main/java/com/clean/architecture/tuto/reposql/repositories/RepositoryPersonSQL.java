package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.clean.architecture.tuto.reposql.config.Config;
import com.clean.architecture.tuto.reposql.config.Config.SingletonSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryPersonSQL implements RepositoryPerson {

    public void createDataSet() throws SQLException {
        Connection connection = Config.SingletonSQL.getInstance().getConnection();
        connection.createStatement()
                .execute("INSERT INTO person (lastname, firstname, age) VALUES ('Romain', 'Chief', 42)");
        connection.createStatement()
                .execute("INSERT INTO person (lastname, firstname, age) VALUES ('Vincent', 'Olivier', 60)");
    }

    public void removeDataSet() throws SQLException {
        Connection connection = SingletonSQL.getInstance().getConnection();
        connection.createStatement()
                .executeUpdate("DELETE FROM person WHERE lastname IN ('Romain', 'Vincent') ");
    }

    @Override
    public Person create(Person person) throws SQLException {
        String SQL_INSERT = "INSERT INTO person (lastname, firstname, age) VALUES (?,?,?)";
        String SQL_GET_ID_INSERTING = "SELECT LAST_INSERT_ID()";

        Connection connection = SingletonSQL.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        preparedStatement.setString(1, person.getLastName());
        preparedStatement.setString(2, person.getFirstName());
        preparedStatement.setInt(3, person.getAge());

        preparedStatement.executeUpdate();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(SQL_GET_ID_INSERTING);

        // rows affected
        rs.next();
        int id = rs.getInt(1);
        person.setId(String.valueOf(id));

        return person;
    }

    @Override
    public List<Person> getAll() throws SQLException {
        List<Person> list = new ArrayList<>();
        Connection connection = SingletonSQL.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM person");
        while (rs.next()) {
            list.add(new Person(rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4)
            ));
        }

        return list;
    }

    @Override
    public Optional<Person> findById(String id) throws SQLException {

        String SQL_SELECT_PERSON = "SELECT * FROM person WHERE id = ?";

        Connection connection = SingletonSQL.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PERSON);
        preparedStatement.setString(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        Person p = null;
        while(rs.next()) {
            p = Person.builder().id(id)
                    .lastName(rs.getString("lastname"))
                    .firstName(rs.getString("firstname"))
                    .age(rs.getInt("age"))
                    .build();
        }
        rs.close();

        return Optional.ofNullable(p);
    }

}
