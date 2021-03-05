package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.DeleteInformations;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Optional;

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

        Mockito.when(this.repository.findById("5"))
                .thenReturn(Optional.of(new Person("5", "Toto", "Tata", 8)));

        DeleteInformations di = this.useCase.execute("5");

        Assertions.assertThat(di).isNotNull();
        Assertions.assertThat(di.getIdsDeleted()).isNotEmpty();
        Assertions.assertThat(di.getIdsDeleted()).hasSize(1);
        Assertions.assertThat(di.getIdsDeleted().get(0)).isEqualTo("5");
    }

    @Test
    public void should_throw_business_exception_when_id_not_exist_in_db() {
        Assertions.assertThatCode(() -> {
                    this.useCase.execute("9");
         }).isInstanceOf(BusinessException.class)
          .hasMessage("L'identifiant 9 n'existe pas");
    }

    @Test
    public void should_throw_business_exception_when_id_use_in_team() {
        Mockito.when(this.repository.existsByIdPerson("1")).thenAnswer(i -> true);

        Assertions.assertThatCode(() -> {
            this.useCase.execute("1");
        }).hasMessage("Cette personne fait partie d'une équipe de 6 joueurs, il ne peut pas la quitter, car une équipe doit être composée de 6 joueurs au minimum.").isInstanceOf(BusinessException.class);

    }

}
