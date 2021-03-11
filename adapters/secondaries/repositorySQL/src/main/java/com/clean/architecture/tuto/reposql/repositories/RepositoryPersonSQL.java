package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryPersonSQL extends AbstractRepositorySQL implements RepositoryPerson {

    public RepositoryPersonSQL() {
        super();
    }

    public void createDataSet() throws SQLException {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        connection.createStatement()
                .execute("INSERT INTO person (uuid, lastname, firstname, age) VALUES (uuid1, 'Romain', 'Chief', 42)");
        connection.createStatement()
                .execute("INSERT INTO person (lastname, firstname, age) VALUES (uuid2, 'Vincent', 'Olivier', 60)");
    }

    public void removeDataSet() throws SQLException {
        connection.createStatement()
                .executeUpdate("DELETE FROM person WHERE lastname IN ('Romain', 'Vincent') ");
    }

    @Override
    public Person create(Person person) throws SQLException, UnsupportedEncodingException {
        String SQL_INSERT = "INSERT INTO person (uuid, lastname, firstname, age) VALUES (?,?,?,?)";
        byte[] uuid = UUID.randomUUID().toString().getBytes("UTF-8");

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        preparedStatement.setBytes(1, uuid);
        preparedStatement.setString(2, person.getLastName());
        preparedStatement.setString(3, person.getFirstName());
        preparedStatement.setInt(4, person.getAge());

        preparedStatement.executeUpdate();

        person.setUuid(uuid);

        return person;
    }

    @Override
    public List<Person> getAll() throws SQLException {
        List<Person> list = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM person");
        while (rs.next()) {
            list.add(new Person(rs.getBytes(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4)
            ));
        }

        return list;
    }

    @Override
    public Optional<Person> findByUuid(byte[] uuid) throws SQLException {

        String SQL_SELECT_PERSON = "SELECT * FROM person WHERE uuid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PERSON);
        preparedStatement.setBytes(1, uuid);

        ResultSet rs = preparedStatement.executeQuery();
        Person p = null;
        while(rs.next()) {
            p = Person.builder().uuid(uuid)
                    .lastName(rs.getString("lastname"))
                    .firstName(rs.getString("firstname"))
                    .age(rs.getInt("age"))
                    .build();
        }
        rs.close();

        return Optional.ofNullable(p);
    }

    @Override
    public Person update(Person personToUpdate) throws SQLException {

        String SQL_UPDATE_PERSON = "UPDATE person SET lastname = ?, firstname = ?, age = ? WHERE uuid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PERSON);
        preparedStatement.setString(1, personToUpdate.getLastName());
        preparedStatement.setString(2, personToUpdate.getFirstName());
        preparedStatement.setInt(3, personToUpdate.getAge());
        preparedStatement.setBytes(4, personToUpdate.getUuid());

        preparedStatement.execute();
        preparedStatement.close();

        return personToUpdate;
    }

    @Override
    public void deleteByUuid(byte[] uuid) throws SQLException {

        String SQL_DELETE_PERSON = "DELETE FROM person WHERE uuid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PERSON);
        preparedStatement.setBytes(1, uuid);

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public boolean existsByUuidPerson(byte[] s) {
        return false;
    }

}
