package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class DisplayDetailsTeamUseCase {

    private RepositoryTeam repository; //interface

    public Optional<Team> execute(String id) throws BusinessException, TechnicalException {
        if(Objects.isNull(id) || id.isEmpty()) {
            throw new BusinessException("L'id d'une équipe est obligatoire");
        } else {
            if(Character.toString(id.charAt(0)).equals("-")) {
                throw new BusinessException("L'id d'une équipe ne peut pas être négatif");
            }
        }

        try {
            return repository.findById(id);
        } catch (TechnicalException e) {
            e.printStackTrace();
            throw e;
        } catch (UnknownHostException | SQLException e) {
            e.printStackTrace();
            throw new TechnicalException(e.getMessage());
        }
    }
}
