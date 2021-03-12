package com.clean.architecture.tuto.core.ports.personne;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RepositoryPerson {

    Person create(Person person) throws UnknownHostException, SQLException, UnsupportedEncodingException;

    List<Person> getAll() throws UnknownHostException, SQLException;

    Optional<Person> findByUuid(String uuid) throws TechnicalException, UnknownHostException, SQLException;

    Person update(Person personToUpdate) throws UnknownHostException, SQLException;

    void deleteByUuid(String id) throws SQLException;

    boolean existsByUuidPerson(String s);
}
