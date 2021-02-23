package com.clean.architecture.tuto.core.ports.personne;

import com.clean.architecture.tuto.core.models.Person;

import java.net.UnknownHostException;
import java.util.List;

public interface RepositoryPerson {

    Person create(Person person) throws UnknownHostException;

    List<Person> getAll() throws UnknownHostException;

    Person display(Person personToDisplay) throws UnknownHostException;
}
