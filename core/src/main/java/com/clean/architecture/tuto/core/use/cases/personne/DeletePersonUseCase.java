package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.DeleteInformations;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import lombok.AllArgsConstructor;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class DeletePersonUseCase {

    private RepositoryPerson repository; //interface

    public DeleteInformations execute(String id) throws BusinessException, SQLException, TechnicalException, UnknownHostException {

        DeleteInformations result = new DeleteInformations();

        isDeletable(id);

        repository.deleteById(id);
        result.addIdDeleted(id);
        return result;
    }

    private void isDeletable(String id) throws BusinessException, TechnicalException, SQLException, UnknownHostException {
        if(Objects.isNull(id) || id.isEmpty()) {
            throw new BusinessException("L'id d'une personne est obligatoire", Collections.singletonList("L'id d'une personne est obligatoire"));
        } else {

            if(Character.toString(id.charAt(0)).equals("-")) {
                throw new BusinessException("L'id d'une personne ne peut pas être négatif", Collections.singletonList("L'id d'une personne ne peut pas être négatif"));
            } else {
                if(repository.existsByIdPerson(id)) {
                    throw new BusinessException("Cette personne fait partie d'une équipe de 6 joueurs, il ne peut pas la quitter, car une équipe doit être composée de 6 joueurs au minimum.", Collections.singletonList("Cette personne fait partie d'une équipe de 6 joueurs, il ne peut pas la quitter, car une équipe doit être composée de 6 joueurs au minimum."));
                }
            }

            this.repository.findById(id)
                    .orElseThrow(() -> new BusinessException("L'identifiant "+ id + " n'existe pas", Collections.singletonList("L'identifiant "+ id + " n'existe pas")));

        }
    }
}
