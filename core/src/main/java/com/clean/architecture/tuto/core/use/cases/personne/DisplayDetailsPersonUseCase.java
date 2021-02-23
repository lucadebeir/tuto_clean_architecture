package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.net.UnknownHostException;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class DisplayDetailsPersonUseCase {

    private RepositoryPerson repository; //interface

    public Person execute(Person person) throws BusinessException, TechnicalException, UnknownHostException {
        if(Objects.isNull(person)) {
            throw new TechnicalException("Person is null");
        } else {
            if(Objects.isNull(person.getId()) || person.getId().isEmpty()) {
                throw new BusinessException("L'id d'une personne est obligatoire");
            } else {
                if(Character.toString(person.getId().charAt(0)).equals("-")) {
                    throw new BusinessException("L'id d'une personne ne peut pas être négatif");
                }
            }
        }
        return repository.display(person);
    }
}
