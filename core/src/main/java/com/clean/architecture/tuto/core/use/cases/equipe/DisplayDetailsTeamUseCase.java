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

    public Optional<Team> execute(byte[] uuid) throws BusinessException, TechnicalException {
        if(Objects.isNull(uuid) || uuid.length == 0) {
            throw new BusinessException("L'uuid d'une équipe est obligatoire");
        }

        try {
            return repository.findByUuid(uuid);
        } catch (TechnicalException e) {
            e.printStackTrace();
            throw e;
        } catch (UnknownHostException | SQLException e) {
            e.printStackTrace();
            throw new TechnicalException(e.getMessage());
        }
    }
}
