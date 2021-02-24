package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class DisplayDetailsTeamUseCase {

    private RepositoryTeam repository; //interface

    public Optional<Team> execute(Team team) throws BusinessException, TechnicalException, UnknownHostException {
        if(Objects.isNull(team)) {
            throw new TechnicalException("Team is null");
        } else {
            if(Objects.isNull(team.getId()) || team.getId().isEmpty()) {
                throw new BusinessException("L'id d'une équipe est obligatoire");
            } else {
                if(Character.toString(team.getId().charAt(0)).equals("-")) {
                    throw new BusinessException("L'id d'une équipe ne peut pas être négatif");
                }
            }
        }
        return repository.findById(team.getId());
    }
}
