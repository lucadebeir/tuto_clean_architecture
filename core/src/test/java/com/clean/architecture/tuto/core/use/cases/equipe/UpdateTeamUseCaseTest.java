package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.clean.architecture.tuto.core.utils.Utils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.rmi.CORBA.Util;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class UpdateTeamUseCaseTest {

    @Mock
    private RepositoryTeam repository;

    private Team teamToUpdate;
    private UpdateTeamUseCase useCase;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        byte[] uuid = Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000");

        this.teamToUpdate = Team.builder()
                .uuid(uuid).name("Liverpool")
                .build();
        this.teamToUpdate.setList(getStubPersons());
        this.useCase = new UpdateTeamUseCase(repository);
    }

    private List<Person> getStubPersons() throws UnsupportedEncodingException {
        Person p1 = new Person(Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000"), "Luca", "Stagiaire", 25);
        Person p2 = new Person(Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440001"), "Abc", "Btagiaire", 25);
        Person p3 = new Person(Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440002"), "Def", "Ctagiaire", 25);
        Person p4 = new Person(Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440003"), "Ghi", "Dtagiaire", 25);
        Person p5 = new Person(Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440004"), "Toto", "Stagiaire", 25);
        Person p6 = new Person(Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440005"), "Tutu", "Stagiaire", 25);
        Person p7 = new Person(Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440006"), "Titi", "Stagiaire", 25);
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.toList());
    }

    @Test
    public void should_throw_business_exception_when_id_is_null() {
        this.teamToUpdate.setUuid(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToUpdate);
        }).hasMessage("L'uuid d'une équipe que l'on modifie ne peut pas être nul").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_name_is_gt_20() {
        this.teamToUpdate.setName("Olympique de Marseille");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToUpdate);
        }).hasMessage("Le nom d'une équipe ne doit pas dépasser 20 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_name_is_lt_2() {
        this.teamToUpdate.setName("a");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToUpdate);
        }).hasMessage("Le nom d'une équipe doit avoir au minimum 2 caracteres").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_name_is_null() {
        this.teamToUpdate.setName(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToUpdate);
        }).hasMessage("Le nom d'une équipe est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_name_is_empty() {
        this.teamToUpdate.setName("");
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToUpdate);
        }).hasMessage("Le nom d'une équipe est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_return_team_when_update_is_a_success() throws BusinessException, TechnicalException, UnknownHostException, SQLException, UnsupportedEncodingException {
        Mockito.when(this.repository.update(this.teamToUpdate)).thenAnswer((i) -> {
            Team t = i.getArgument(0);
            return t;
        });
        Team team = this.useCase.execute(teamToUpdate);
        Assertions.assertThat(team).isNotNull();
        Assertions.assertThat(team.getUuid()).isNotNull();
        Assertions.assertThat(team.getName()).isEqualTo("Liverpool");
        List<Person> stubPersons = getStubPersons();
        team.getList().forEach(person -> {
            Assertions.assertThat(stubPersons.stream().anyMatch(p -> Utils.getGuidFromByteArray(p.getUuid()).equals(Utils.getGuidFromByteArray(person.getUuid())))).isTrue();
            Assertions.assertThat(stubPersons.stream().anyMatch(p -> p.getFirstName().equals(person.getFirstName()))).isTrue();
            Assertions.assertThat(stubPersons.stream().anyMatch(p -> p.getLastName().equals(person.getLastName()))).isTrue();
        });
    }

    @Test
    public void should_throw_business_exception_when_list_of_person_is_null() {
        this.teamToUpdate.setList(null);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToUpdate);
        }).hasMessage("Une équipe doit obligatoirement être lié à des personnes").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_list_of_person_is_empty() {
        this.teamToUpdate.setList(new ArrayList<>());
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToUpdate);
        }).hasMessage("Une équipe doit obligatoirement être lié à des personnes").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_list_of_person_is_gt_11() throws UnsupportedEncodingException {
        byte[] uuid = Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000");
        List<Person> list = new ArrayList<>();
        Person person = new Person(uuid, "Luca", "Debeir", 25);
        for(int i = 0; i<13; i++) {
            list.add(person);
        }
        this.teamToUpdate.setList(list);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToUpdate);
        }).hasMessage("Une équipe ne peut être composée que de 11 personnes au maximum").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_list_of_person_is_lt_6() throws UnsupportedEncodingException {
        byte[] uuid = Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000");
        List<Person> list = new ArrayList<>();
        Person person = new Person(uuid, "Luca", "Debeir", 25);
        list.add(person);
        this.teamToUpdate.setList(list);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToUpdate);
        }).hasMessage("Une équipe ne peut être composée que de 6 personnes au minimum").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_person_is_minor() throws UnsupportedEncodingException {
        List<Person> list = getStubPersons();
        list.get(0).setAge(16);
        this.teamToUpdate.setList(list);
        Assertions.assertThatCode(() -> {
            this.useCase.execute(teamToUpdate);
        }).hasMessage("Une équipe ne peut être composée que de personnes majeures").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_throw_business_exception_when_name_is_not_unique() throws UnknownHostException, SQLException, UnsupportedEncodingException {
        byte[] uuid = Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000");

        Mockito.when(this.repository.existsByName("OL")).thenReturn(true);

        Team team = Team.builder().uuid(uuid).name("OL").list(getStubPersons()).build();
        Assertions.assertThatCode(() -> {
            this.useCase.execute(team);
        }).hasMessage("Une équipe portant ce nom existe déjà").isInstanceOf(BusinessException.class);
    }
}
