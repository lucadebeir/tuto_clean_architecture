package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.DeleteInformations;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.clean.architecture.tuto.core.utils.Utils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DeletePersonUseCaseTest {

    @Mock
    private RepositoryPerson repository;

    private DeletePersonUseCase useCase;

    //constructeur pour les tests, variables communes à tous les tests
    @Before
    public void setUp() {
        this.useCase = new DeletePersonUseCase(repository);
    }

    @Test
    public void should_success_when_id_not_exist_in_db() throws TechnicalException, SQLException, UnknownHostException, BusinessException {
        byte[] uuid = Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000");

        Mockito.when(this.repository.findByUuid(uuid))
                .thenReturn(Optional.of(new Person(uuid, "Toto", "Tata", 8)));

        DeleteInformations di = this.useCase.execute(uuid);

        Assertions.assertThat(di).isNotNull();
        Assertions.assertThat(di.getUuidsDeleted()).isNotEmpty();
        Assertions.assertThat(di.getUuidsDeleted()).hasSize(1);
        Assertions.assertThat(di.getUuidsDeleted().get(0)).isEqualTo(Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000"));
    }

    @Test
    public void should_throw_business_exception_when_id_not_exist_in_db() {
        byte[] uuid = Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000");

        Assertions.assertThatCode(() -> {
                    this.useCase.execute(uuid);
         }).isInstanceOf(BusinessException.class)
          .hasMessage("L'identifiant 123e4567-e89b-12d3-a456-556642440000 n'existe pas");
    }

    @Test
    public void should_throw_business_exception_when_id_use_in_team() {
        byte[] uuid = Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000");

        Mockito.when(this.repository.existsByUuidPerson(uuid)).thenAnswer(i -> true);

        Assertions.assertThatCode(() -> {
            this.useCase.execute(uuid);
        }).hasMessage("Cette personne fait partie d'une équipe de 6 joueurs, il ne peut pas la quitter, car une équipe doit être composée de 6 joueurs au minimum.").isInstanceOf(BusinessException.class);

    }

}
