package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
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
public class DisplayDetailsPersonUseCaseTest {

    @Mock
    private RepositoryPerson repository;

    private Person personToDisplay;
    private DisplayDetailsPersonUseCase useCase;

    //constructeur pour les tests, variables communes à tous les tests
    @Before
    public void setUp() {
        byte[] uuid = Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000");

        this.personToDisplay = new Person(uuid, "Luca", "Debeir", 25);
        this.useCase = new DisplayDetailsPersonUseCase(repository);
    }

    @Test
    public void should_return_person_when_display_is_a_success() throws BusinessException, TechnicalException, UnknownHostException, SQLException {
        Mockito.when(this.repository.findByUuid(this.personToDisplay.getUuid())).thenReturn(Optional.of(this.personToDisplay));
        Optional<Person> optPerson = this.useCase.execute(personToDisplay.getUuid());
        Assertions.assertThat(optPerson).isNotNull();
        Assertions.assertThat(optPerson).isPresent();
        optPerson.ifPresent(person -> {
            Assertions.assertThat(Utils.getGuidFromByteArray(person.getUuid())).isEqualTo(Utils.getGuidFromByteArray(Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000")));
            Assertions.assertThat(person.getFirstName()).isEqualTo("Debeir");
            Assertions.assertThat(person.getLastName()).isEqualTo("Luca");
            Assertions.assertThat(person.getAge()).isEqualTo(25);
        });
    }

    @Test
    public void should_return_optional_empty_when_id_doesnt_exist_in_db() throws BusinessException, TechnicalException, UnknownHostException, SQLException, UnsupportedEncodingException {
        byte[] uuid = Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000");

        Mockito.when(this.repository.findByUuid(uuid))
                .thenReturn(Optional.empty());
        Optional<Person> optPerson = this.useCase.execute(uuid);
        Assertions.assertThat(optPerson).isNotPresent();
    }

    @Test
    public void should_throw_business_exception_when_id_is_null() {
        Assertions.assertThatCode(() -> {
            this.useCase.execute(null);
        }).hasMessage("L'uuid d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }
}
