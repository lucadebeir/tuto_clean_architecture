package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
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

    public void execute(byte[] uuid) throws BusinessException, TechnicalException, SQLException, UnknownHostException {
        isDeletable(uuid);
        repository.deleteByUuid(uuid);
    }

    private void isDeletable(byte[] uuid) throws BusinessException, TechnicalException, SQLException, UnknownHostException {
        if(Objects.isNull(uuid) || uuid.length == 0) {
            throw new BusinessException("L'uuid d'une personne est obligatoire");
        } else {
            this.repository.findByUuid(uuid)
                    .orElseThrow(() -> new BusinessException("L'identifiant "+ Utils.getGuidFromByteArray(uuid) + " n'existe pas", Collections.singletonList("L'identifiant "+ UUID.nameUUIDFromBytes(uuid).toString() + " n'existe pas")));

        }
    }
}
