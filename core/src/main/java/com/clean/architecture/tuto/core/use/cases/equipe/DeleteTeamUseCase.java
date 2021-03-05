package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class DeleteTeamUseCase {

    private RepositoryTeam repository;

    public void execute(String id) throws BusinessException {
        isDeletable(id);
        repository.deleteById(id);
    }

    private void isDeletable(String id) throws BusinessException {
        if(Objects.isNull(id) || id.isEmpty()) {
            throw new BusinessException("L'id d'une personne est obligatoire");
        } else {
            if(Character.toString(id.charAt(0)).equals("-")) {
                throw new BusinessException("L'id d'une personne ne peut pas être négatif");
            }
        }
    }
}
