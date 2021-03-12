package com.clean.architecture.tuto.core.use.cases.personne;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
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
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class UpdatePersonUseCaseTest {

    @Mock
    private RepositoryPerson repository;

    private Person personToUpdate;
    private UpdatePersonUseCase useCase;

    //constructeur pour les tests, variables communes à tous les tests
    @Before
    public void setUp() {
        this.personToUpdate = Person.builder()
                .uuid("123e4567-e89b-12d3-a456-556642440000")
                .lastName("Debeir")
                .firstName("Luca")
                .age(25)
                .build();
        this.useCase = new UpdatePersonUseCase(repository);
    }

    @Test
    public void should_return_person_when_update_is_a_success() throws BusinessException, TechnicalException, UnknownHostException, SQLException {
        Mockito.when(this.repository.update(this.personToUpdate)).thenAnswer(i -> {
            Person p = i.getArgument(0);
            return p;
        });

        Person person = this.useCase.execute(personToUpdate);
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.getUuid()).isNotNull();
        Assertions.assertThat(person.getFirstName()).isEqualTo("Luca");
        Assertions.assertThat(person.getLastName()).isEqualTo("Debeir");
        Assertions.assertThat(person.getAge()).isEqualTo(25);
    }

    @Test
    public void should_throw_business_exception_when_id_is_null() {
        this.personToUpdate.setUuid(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("L'uuid d'une personne que l'on modifie ne peut pas être nul").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_lastname_is_gt_30() {
        this.personToUpdate.setLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("Le nom d'une personne ne doit pas dépasser 30 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_lastname_is_lt_2() {
        this.personToUpdate.setLastName("a");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("Le nom d'une personne doit avoir au minimum 2 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_firstname_is_gt_40() {
        this.personToUpdate.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("Le prenom d'une personne ne doit pas dépasser 40 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_firstname_is_lt_2() {
        this.personToUpdate.setFirstName("a");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("Le prenom d'une personne doit avoir au minimum 2 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_lastname_is_lt_2_and_firstname_is_lt_2() {
        this.personToUpdate.setFirstName("a");
        this.personToUpdate.setLastName("a");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessageContaining("Le nom d'une personne doit avoir au minimum 2 caracteres")
                .hasMessageContaining("Le prenom d'une personne doit avoir au minimum 2 caracteres")
                .isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_lastname_is_null() {
        this.personToUpdate.setLastName(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("Le nom d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_firstname_is_null() {
        this.personToUpdate.setFirstName(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("Le prenom d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_age_is_null() {
        this.personToUpdate.setAge(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("L'age d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_lastname_is_empty() {
        this.personToUpdate.setLastName("");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("Le nom d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_firstname_is_empty() {
        this.personToUpdate.setFirstName("");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("Le prenom d'une personne est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_age_is_lte_0() {
        this.personToUpdate.setAge(0);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("L'age d'une personne doit etre positive").isInstanceOf(BusinessException.class);


        this.personToUpdate.setAge(-11);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(personToUpdate);
        }).hasMessage("L'age d'une personne doit etre positive").isInstanceOf(BusinessException.class);
    }
}
