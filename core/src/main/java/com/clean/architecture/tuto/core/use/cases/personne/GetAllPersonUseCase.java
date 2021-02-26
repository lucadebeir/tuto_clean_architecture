package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import lombok.AllArgsConstructor;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class GetAllPersonUseCase {

    private RepositoryPerson repository; //interface

    public List<Person> execute() throws TechnicalException {
        try {
            return repository.getAll();
        } catch (UnknownHostException | SQLException e) {
            e.printStackTrace();
            throw new TechnicalException(e.getMessage());
        }
    }
}
