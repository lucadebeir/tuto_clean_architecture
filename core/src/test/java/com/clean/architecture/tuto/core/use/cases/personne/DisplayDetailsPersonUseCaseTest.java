package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
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
        Mockito.when(this.repository.display(this.personToDisplay)).thenAnswer((i) -> {
            Person p = i.getArgument(0);
            return p;
        });
        Person person = this.useCase.execute(personToDisplay);
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.getId()).isNotNull();
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
