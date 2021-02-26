package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import org.assertj.core.api.Assertions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class CreateTeamUseCaseTest {

    @Mock
    private RepositoryTeam repository;

    private Team teamToCreate;
    private CreateTeamUseCase useCase;

    //constructeur pour les tests, variables communes à tous les tests
    @Before
    public void setUp() {
        this.teamToCreate = Team.builder()
                .id("10").name("Liverpool")
                .build();
        this.teamToCreate.setList(getStubPersons());
        this.useCase = new CreateTeamUseCase(repository);
    }

    private List<Person> getStubPersons() {
        Person p1 = new Person("1", "Luca", "Stagiaire", 25);
        Person p2 = new Person("2", "Abc", "Btagiaire", 25);
        Person p3 = new Person("3", "Def", "Ctagiaire", 25);
        Person p4 = new Person("4", "Ghi", "Dtagiaire", 25);
        Person p5 = new Person("5", "Toto", "Stagiaire", 25);
        Person p6 = new Person("6", "Tutu", "Stagiaire", 25);
        Person p7 = new Person("7", "Titi", "Stagiaire", 25);
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.toList());
    }

    @Test
    public void should_throw_business_exception_when_name_is_gt_20() {
        this.teamToCreate.setName("Olympique de Marseille");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToCreate);
        }).hasMessage("Le nom d'une équipe ne doit pas dépasser 20 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_name_is_lt_2() {
        this.teamToCreate.setName("a");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToCreate);
        }).hasMessage("Le nom d'une équipe doit avoir au minimum 2 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_name_is_null() {
        this.teamToCreate.setName(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToCreate);
        }).hasMessage("Le nom d'une équipe est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_name_is_empty() {
        this.teamToCreate.setName("");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToCreate);
        }).hasMessage("Le nom d'une équipe est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_return_team_when_creation_is_a_success() throws BusinessException, TechnicalException, UnknownHostException, SQLException {
        Mockito.when(this.repository.create(this.teamToCreate)).thenAnswer((i) -> {
            Team t = i.getArgument(0);
            t.setId("1");
            return t;
        });
        Team team = this.useCase.execute(teamToCreate);
        Assertions.assertThat(team).isNotNull();
        Assertions.assertThat(team.getId()).isNotNull();
        Assertions.assertThat(team.getName()).isEqualTo("Liverpool");
        List<Person> stubPersons = getStubPersons();
        team.getList().forEach(person -> {
            Assertions.assertThat(stubPersons.stream().anyMatch(p -> p.getId().equals(person.getId()))).isTrue();
            Assertions.assertThat(stubPersons.stream().anyMatch(p -> p.getFirstName().equals(person.getFirstName()))).isTrue();
            Assertions.assertThat(stubPersons.stream().anyMatch(p -> p.getLastName().equals(person.getLastName()))).isTrue();
        });
    }

    @Test
    public void should_throw_business_exception_when_list_of_person_is_null() {
        this.teamToCreate.setList(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToCreate);
        }).hasMessage("Une équipe doit obligatoirement être lié à des personnes").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_list_of_person_is_empty() {
        this.teamToCreate.setList(new ArrayList<>());
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToCreate);
        }).hasMessage("Une équipe doit obligatoirement être lié à des personnes").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_list_of_person_is_gt_11() {
        List<Person> list = new ArrayList<>();
        Person person = new Person("1", "Luca", "Debeir", 25);
        for(int i = 0; i<13; i++) {
            list.add(person);
        }
        this.teamToCreate.setList(list);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToCreate);
        }).hasMessage("Une équipe ne peut être composée que de 11 personnes au maximum").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_list_of_person_is_lt_6() {
        List<Person> list = new ArrayList<>();
        Person person = new Person("1", "Luca", "Debeir", 25);
        list.add(person);
        this.teamToCreate.setList(list);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToCreate);
        }).hasMessage("Une équipe ne peut être composée que de 6 personnes au minimum").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_person_is_minor() {
        List<Person> list = getStubPersons();
        list.get(0).setAge(16);
        this.teamToCreate.setList(list);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToCreate);
        }).hasMessage("Une équipe ne peut être composée que de personnes majeures").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_name_is_not_unique() throws UnknownHostException, SQLException {

        Mockito.when(this.repository.existsByName("OL")).thenReturn(true);

        Team team = Team.builder().name("OL").list(getStubPersons()).build();
        Assertions.assertThatCode(() -> {
            this.useCase.execute(team);
        }).hasMessage("Une équipe portant ce nom existe déjà").isInstanceOf(BusinessException.class);
    }

}
