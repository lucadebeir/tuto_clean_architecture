package com.clean.architecture.tuto.reposql.config.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.reposql.repositories.RepositoryPersonSQL;
import com.clean.architecture.tuto.reposql.repositories.RepositoryTeamSQL;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class RepositoryTeamSQL_UT {

    private RepositoryTeamSQL repositoryTeam;
    private RepositoryPersonSQL repositoryPerson;

    @Before
    public void setUp() throws SQLException {
        // TODO : Ne fais pas ce que je fais :
        this.repositoryTeam = new RepositoryTeamSQL();
        this.repositoryPerson = new RepositoryPersonSQL();
        this.repositoryTeam.createDataSet();
    }

    @After
    public void tearDown() throws SQLException {
        // Ca c'est bon
        this.repositoryTeam.removeDataSet();
    }

    @Test
    public void creer_team_should_return_team_with_id_and_some_informations() throws SQLException {
        List<Person> list = new ArrayList<>();
        Person p = this.repositoryPerson.create(Person.builder()
                .age(18)
                .firstName("Titeuf")
                .lastName("Eponge")
                .build());
        list.add(p);
        Team t = Team.builder().name("PSG")
                .list(list)
                .build();
        Team team = this.repositoryTeam.create(t);
        Assertions.assertThat(team).isNotNull();
        Assertions.assertThat(team.getId()).isNotNull();
        Assertions.assertThat(team.getId()).isNotEmpty();

        Integer id = Integer.valueOf(team.getId());
        Assertions.assertThat(id).isGreaterThan(0);

        Assertions.assertThat(team.getName()).isEqualTo("PSG");
    }

    @Test
    public void get_all_should_return_at_least_Arsenal_and_MU() throws SQLException {
        List<Team> teamAll = this.repositoryTeam.getAll();

        Assertions.assertThat(teamAll).isNotNull();
        Assertions.assertThat(teamAll).isNotEmpty();
        Assertions.assertThat(teamAll).hasSizeGreaterThanOrEqualTo(2); // strict minimum syndical

        boolean isArsenalPresent = teamAll.stream().anyMatch(t -> t.getName().equals("Arsenal") && !t.getList().isEmpty());
        Assertions.assertThat(isArsenalPresent).isTrue();

        boolean isRomainPresentArsenal = teamAll.stream().anyMatch(t -> t.getName().equals("Arsenal") &&
                t.getList().stream().anyMatch(person ->
                        person.getFirstName().equals("Romain")
                                && person.getLastName().equals("Chief")
                                && person.getAge().equals(42)
                )
        );
        Assertions.assertThat(isRomainPresentArsenal).isTrue();

        boolean isMUPresent = teamAll.stream().anyMatch(p -> p.getName().equals("Manchester United") && !p.getList().isEmpty());
        Assertions.assertThat(isMUPresent).isTrue();

        boolean isVincentPresentMU = teamAll.stream().anyMatch(t ->
                        t.getName().equals("Manchester United") && t.getList().stream().anyMatch(person ->
                                person.getFirstName().equals("Vincent")
                                    && person.getLastName().equals("Olivier")
                                    && person.getAge().equals(60)
                )
        );
        Assertions.assertThat(isVincentPresentMU).isTrue();
    }

    @Test
    public void find_by_id_should_return_empty_optional_when_id_is_unknown() throws SQLException {
        Assertions.assertThat(this.repositoryTeam.findById("99999")).isNotPresent();
    }

    @Test
    public void find_by_id_should_return_optional_when_id_is_team_created() throws SQLException {
        List<Person> list = new ArrayList<>();
        Person p = this.repositoryPerson.create(Person.builder()
                .age(18)
                .firstName("Titeuf")
                .lastName("Eponge")
                .build());
        list.add(p);
        Team t = Team.builder().name("PSG")
                .list(list)
                .build();
        Team team = this.repositoryTeam.create(t);
        Assertions.assertThat(this.repositoryTeam.findById(team.getId())).isPresent();

        this.repositoryTeam.findById(team.getId())
                .ifPresent(tt -> {
                    Assertions.assertThat(tt.getName()).isEqualTo("PSG");
                    //Assertions.assertThat(tt.getList().contains(p)); // YASSINE : Le problème avec cette approche est qu'il faut
                    // dans 90% des cas avoir fait un equals et hashCode. Une solution qui marche à 100% consiste à faire un
                    // anyMatch :
                    Assertions.assertThat(tt.getList().stream().anyMatch(person -> person.getId().equals(p.getId()) ));
                });
    }
}
