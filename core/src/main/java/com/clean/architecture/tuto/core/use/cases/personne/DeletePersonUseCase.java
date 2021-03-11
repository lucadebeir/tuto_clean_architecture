package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.DeleteInformations;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.clean.architecture.tuto.core.utils.Utils;
import lombok.AllArgsConstructor;

import javax.rmi.CORBA.Util;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class DeletePersonUseCase {

    private RepositoryPerson repository; //interface

    public DeleteInformations execute(byte[] uuid) throws BusinessException, SQLException, TechnicalException, UnknownHostException {

        DeleteInformations result = new DeleteInformations();

        isDeletable(uuid);

        repository.deleteByUuid(uuid);
        result.addUuidDeleted(uuid);
        return result;
    }

    private void isDeletable(byte[] uuid) throws BusinessException, TechnicalException, SQLException, UnknownHostException {
        if(Objects.isNull(uuid) || uuid.length == 0) {
            throw new BusinessException("L'uuid d'une personne est obligatoire", Collections.singletonList("L'uuid d'une personne est obligatoire"));
        } else {

            if(repository.existsByUuidPerson(uuid)) {
                throw new BusinessException("Cette personne fait partie d'une équipe de 6 joueurs, il ne peut pas la quitter, car une équipe doit être composée de 6 joueurs au minimum.", Collections.singletonList("Cette personne fait partie d'une équipe de 6 joueurs, il ne peut pas la quitter, car une équipe doit être composée de 6 joueurs au minimum."));
            }

            this.repository.findByUuid(uuid)
                    .orElseThrow(() -> new BusinessException("L'identifiant "+ Utils.getGuidFromByteArray(uuid) + " n'existe pas", Collections.singletonList("L'identifiant "+ UUID.nameUUIDFromBytes(uuid).toString() + " n'existe pas")));

        }
    }
}
