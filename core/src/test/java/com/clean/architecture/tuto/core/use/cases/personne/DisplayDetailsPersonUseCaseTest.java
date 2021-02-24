package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DisplayDetailsPersonUseCaseTest {

    @Mock
    private RepositoryPerson repository;

    private Person personToDisplay;
    private DisplayDetailsPersonUseCase useCase;

    //constructeur pour les tests, variables communes à tous les tests
    @Before
    public void setUp() {
        this.personToDisplay = new Person("1", "Luca", "Debeir", 25);
        this.useCase = new DisplayDetailsPersonUseCase(repository);
    }

    @Test
    public void should_return_person_when_display_is_a_success() throws BusinessException, TechnicalException, UnknownHostException {
        Mockito.when(this.repository.findById(this.personToDisplay.getId())).thenReturn(Optional.of(this.personToDisplay));
        Optional<Person> optPerson = this.useCase.execute(personToDisplay);
        Assertions.assertThat(optPerson).isNotNull();
        Assertions.assertThat(optPerson).isPresent();
        optPerson.ifPresent(person -> {
            Assertions.assertThat(person.getId()).isEqualTo("1");
            Assertions.assertThat(person.getFirstName()).isEqualTo("Luca");
            Assertions.assertThat(person.getLastName()).isEqualTo("Debeir");
            Assertions.assertThat(person.getAge()).isEqualTo(25);
        });
    }

    @Test
    public void should_return_optional_empty_when_id_doesnt_exist_in_db() throws BusinessException, TechnicalException, UnknownHostException {
        Mockito.when(this.repository.findById("9"))
                .thenReturn(Optional.empty());
        Optional<Person> optPerson = this.useCase.execute(Person.builder().id("9").build());
        Assertions.assertThat(optPerson).isNotPresent();
    }

    @Test
    public void should_throw_business_exception_when_id_is_null() {
        this.personToDisplay.setId(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToDisplay);
        }).hasMessage("L'id d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_id_is_empty() {
        this.personToDisplay.setId("");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToDisplay);
        }).hasMessage("L'id d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_id_is_lt_0() {
        this.personToDisplay.setId("-1");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToDisplay);
        }).hasMessage("L'id d'une personne ne peut pas être négatif").isInstanceOf(BusinessException.class);
    }
}
