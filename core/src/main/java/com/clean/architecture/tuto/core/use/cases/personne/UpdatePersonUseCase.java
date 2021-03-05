package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonUseCase {

    private RepositoryPerson repository; //interface

    public Person execute(Person person) throws TechnicalException, BusinessException, SQLException, UnknownHostException {
        checkBusinessRules(person);
        try {
            return repository.update(person);
        } catch (UnknownHostException | SQLException e) {
            e.printStackTrace();
            throw new TechnicalException(e.getMessage());
        }
    }

    private void checkBusinessRules(Person person) throws BusinessException, TechnicalException, SQLException, UnknownHostException {
        List<String> errorsList = new ArrayList<>();
        if(Objects.isNull(person)) {
            throw new TechnicalException("Person is null");
        } else {
            this.repository.findById(person.getId())
                    .orElseThrow(() -> new BusinessException("L'identifiant "+ person.getId() + " n'existe pas", Collections.singletonList("L'identifiant "+ person.getId() + " n'existe pas")));

            testStringMandatory(errorsList, person.getLastName(), "nom", 30, 2);
            testStringMandatory(errorsList, person.getFirstName(), "prenom", 40, 2);
            Integer age = person.getAge();
            if (Objects.isNull(person.getId())) {
                errorsList.add("L'id d'une personne que l'on modifie ne peut pas être nul");
            }
            if(Objects.isNull(age)) {
                errorsList.add("L'age d'une personne est obligatoire");
            } else {
                if(age <= 0) {
                    errorsList.add("L'age d'une personne doit etre positive");
                }
            }
            if(!errorsList.isEmpty()) {
                throw new BusinessException(String.join(",", errorsList), errorsList);
            }
        }
    }

    private void testStringMandatory(List<String> errorsList, String str, String fieldName, int maxLength, int minLength) {
        if(StringUtils.isEmpty(str)) {
            errorsList.add("Le " + fieldName+ " d'une personne est obligatoire");
        } else {
            if(str.length() > maxLength) {
                errorsList.add("Le " + fieldName + " d'une personne ne doit pas dépasser " + maxLength + " caracteres");
            }
            if(str.length() < minLength) {
                errorsList.add("Le " + fieldName + " d'une personne doit avoir au minimum " + minLength + " caracteres");
            }
        }
    }
}
