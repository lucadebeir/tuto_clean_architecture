package com.clean.architecture.tuto.core.ports.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.models.Person;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

public interface RepositoryPerson {

    Person create(Person person) throws UnknownHostException;

    List<Person> getAll() throws UnknownHostException;

    Optional<Person> findById(String id) throws UnknownHostException, BusinessException;
}
