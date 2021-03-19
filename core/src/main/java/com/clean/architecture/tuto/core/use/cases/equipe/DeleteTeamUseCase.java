package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.DeleteInformations;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.clean.architecture.tuto.core.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class DeleteTeamUseCase {

    private RepositoryTeam repository;

    public DeleteInformations execute(String uuid) throws BusinessException, TechnicalException, SQLException, UnknownHostException {
        DeleteInformations result = new DeleteInformations();
        isDeletable(uuid);
        repository.deleteByUuid(uuid);
        result.addUuidDeleted(uuid);
        return result;
    }

    private void isDeletable(String uuid) throws BusinessException, TechnicalException, SQLException, UnknownHostException {
        if(Objects.isNull(uuid) || uuid.isEmpty()) {
            throw new BusinessException("L'uuid d'une personne est obligatoire");
        } else {
            this.repository.findByUuid(uuid)
                    .orElseThrow(() -> new BusinessException("L'identifiant "+ uuid + " n'existe pas", Collections.singletonList("L'identifiant "+ uuid + " n'existe pas")));

        }
    }
}
