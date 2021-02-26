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
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class CreatePersonUseCaseTest {

    @Mock
    private RepositoryPerson repository;

    private Person personToCreate;
    private CreatePersonUseCase useCase;

    //constructeur pour les tests, variables communes à tous les tests
    @Before
    public void setUp() {
        this.personToCreate = new Person(null,"Luca", "Debeir", 25);
        this.useCase = new CreatePersonUseCase(repository);
    }

    @Test
    public void should_return_person_when_creation_is_a_success() throws BusinessException, TechnicalException, UnknownHostException, SQLException {
        Mockito.when(this.repository.create(this.personToCreate)).thenAnswer((i) -> {
            Person p = i.getArgument(0);
            p.setId("1");
            return p;
        });
        Person person = this.useCase.execute(personToCreate);
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.getId()).isNotNull();
        Assertions.assertThat(person.getFirstName()).isEqualTo("Luca");
        Assertions.assertThat(person.getLastName()).isEqualTo("Debeir");
        Assertions.assertThat(person.getAge()).isEqualTo(25);
    }

    @Test
    public void should_throw_business_exception_when_lastname_is_gt_30() {
        this.personToCreate.setLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("Le nom d'une personne ne doit pas dépasser 30 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_lastname_is_lt_2() {
        this.personToCreate.setLastName("a");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("Le nom d'une personne doit avoir au minimum 2 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_firstname_is_gt_40() {
        this.personToCreate.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("Le prenom d'une personne ne doit pas dépasser 40 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_firstname_is_lt_2() {
        this.personToCreate.setFirstName("a");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("Le prenom d'une personne doit avoir au minimum 2 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_lastname_is_lt_2_and_firstname_is_lt_2() {
        this.personToCreate.setFirstName("a");
        this.personToCreate.setLastName("a");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessageContaining("Le nom d'une personne doit avoir au minimum 2 caracteres")
          .hasMessageContaining("Le prenom d'une personne doit avoir au minimum 2 caracteres")
                .isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_lastname_is_null() {
        this.personToCreate.setLastName(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("Le nom d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_firstname_is_null() {
        this.personToCreate.setFirstName(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("Le prenom d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_age_is_null() {
        this.personToCreate.setAge(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("L'age d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_lastname_is_empty() {
        this.personToCreate.setLastName("");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("Le nom d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_firstname_is_empty() {
        this.personToCreate.setFirstName("");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("Le prenom d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_age_is_lte_0() {
        this.personToCreate.setAge(0);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("L'age d'une personne doit etre positive").isInstanceOf(BusinessException.class);


        this.personToCreate.setAge(-11);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToCreate);
        }).hasMessage("L'age d'une personne doit etre positive").isInstanceOf(BusinessException.class);
    }
}
