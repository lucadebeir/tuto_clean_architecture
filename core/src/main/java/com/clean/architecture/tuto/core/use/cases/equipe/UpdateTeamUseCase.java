package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeamUseCase {

    private RepositoryTeam repository;


    public Team execute(Team team) throws TechnicalException, BusinessException {
        checkBusinessRules(team);
        try {
            return repository.update(team);
        } catch (UnknownHostException | SQLException e) {
            e.printStackTrace(); // LOG
            throw new TechnicalException(e.getMessage());
        }
    }

    private void checkBusinessRules(Team team) throws BusinessException, TechnicalException {
        List<String> errorsList = new ArrayList<>();
        if(Objects.isNull(team)) {
            throw new TechnicalException("Team is null");
        } else {
            if(Objects.isNull(team.getUuid())) {
                errorsList.add("L'uuid d'une équipe que l'on modifie ne peut pas être nul");
            } else {
                testStringMandatory(errorsList, team.getName(), "nom", 20, 2);

                try {
                    if(StringUtils.isNotEmpty(team.getName()) && this.repository.existsByName(team.getName())) {
                        errorsList.add("Une équipe portant ce nom existe déjà");
                    }
                } catch (UnknownHostException | SQLException e) {
                    e.printStackTrace();
                    throw new TechnicalException(e.getMessage());
                }
                if(CollectionUtils.isEmpty(team.getList())) {
                    errorsList.add("Une équipe doit obligatoirement être lié à des personnes");
                } else {
                    if(team.getList().size() < 6) {
                        errorsList.add("Une équipe ne peut être composée que de 6 personnes au minimum");
                    }
                    if(team.getList().size() > 11) {
                        errorsList.add("Une équipe ne peut être composée que de 11 personnes au maximum");
                    }
                    team.getList().forEach(person -> {
                        if(person.getAge() < 18) {
                            if(!errorsList.contains("Une équipe ne peut être composée que de personnes majeures")) {
                                errorsList.add("Une équipe ne peut être composée que de personnes majeures");
                            }
                        }
                    });
                }
            }
        }

        if(!errorsList.isEmpty()) {
            throw new BusinessException(String.join(",", errorsList), errorsList);
        }
    }

    private void testStringMandatory(List<String> errorsList, String str, String fieldName, int maxLength, int minLength) {
        if(Objects.isNull(str) || str.isEmpty()) {
            errorsList.add("Le " + fieldName+ " d'une équipe est obligatoire");
        } else {
            if(str.length() > maxLength) {
                errorsList.add("Le " + fieldName + " d'une équipe ne doit pas dépasser " + maxLength + " caracteres");
            }
            if(str.length() < minLength) {
                errorsList.add("Le " + fieldName + " d'une équipe doit avoir au minimum " + minLength + " caracteres");
            }
        }
    }

}
