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

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class DisplayDetailsTeamUseCaseTest {

    @Mock
    private RepositoryTeam repository;

    private Team teamToDisplay;
    private DisplayDetailsTeamUseCase useCase;

    //constructeur pour les tests, variables communes à tous les tests
    @Before
    public void setUp() {
        this.teamToDisplay = Team.builder()
                .uuid("123e4567-e89b-12d3-a456-556642440000")
                .name("OM")
                .list(getStubPersons())
                .build();
        this.useCase = new DisplayDetailsTeamUseCase(repository);
    }

    private List<Person> getStubPersons() {
        Person p1 = new Person("123e4567-e89b-12d3-a456-556642440000", "Luca", "Stagiaire", 25);
        Person p2 = new Person("123e4567-e89b-12d3-a456-556642440001", "Abc", "Btagiaire", 25);
        Person p3 = new Person("123e4567-e89b-12d3-a456-556642440002", "Def", "Ctagiaire", 25);
        Person p4 = new Person("123e4567-e89b-12d3-a456-556642440003", "Ghi", "Dtagiaire", 25);
        Person p5 = new Person("123e4567-e89b-12d3-a456-556642440004", "Toto", "Stagiaire", 25);
        Person p6 = new Person("123e4567-e89b-12d3-a456-556642440005", "Tutu", "Stagiaire", 25);
        Person p7 = new Person("123e4567-e89b-12d3-a456-556642440006", "Titi", "Stagiaire", 25);
        return Stream.of(p1, p2, p3, p4, p5, p6, p7)
                .collect(Collectors.toList());
    }

    @Test
    public void should_return_team_when_display_is_a_success() throws BusinessException, TechnicalException, UnknownHostException, SQLException {
        Mockito.when(this.repository.findByUuid(this.teamToDisplay.getUuid())).thenReturn(Optional.of(this.teamToDisplay));
        Optional<Team> optTeam = this.useCase.execute("123e4567-e89b-12d3-a456-556642440000");
        Assertions.assertThat(optTeam).isPresent();
        optTeam.ifPresent(team -> {
            Assertions.assertThat(team.getUuid()).isEqualTo("123e4567-e89b-12d3-a456-556642440000");
            Assertions.assertThat(team.getName()).isEqualTo("OM");
            Assertions.assertThat(team.getList()).hasSize(7);
        });
    }

    @Test
    public void should_throw_business_exception_when_id_is_null() {
        Assertions.assertThatCode(() -> {
            this.useCase.execute(null);
        }).hasMessage("L'uuid d'une équipe est obligatoire").isInstanceOf(BusinessException.class);
    }

    @Test
    public void should_return_optional_empty_when_id_doesnt_exist_in_db() throws BusinessException, TechnicalException, UnknownHostException, SQLException {
        Mockito.when(this.repository.findByUuid("123e4567-e89b-12d3-a456-556642440000"))
                .thenReturn(Optional.empty());
        Optional<Team> optTeam = this.useCase.execute("123e4567-e89b-12d3-a456-556642440000");
        Assertions.assertThat(optTeam).isNotPresent();
    }

}
