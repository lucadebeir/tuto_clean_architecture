package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.DeleteInformations;
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
public class DeleteTeamUseCaseTest {

    @Mock
    private RepositoryTeam repository;

    private DeleteTeamUseCase useCase;

    @Before
    public void setUp() { this.useCase = new DeleteTeamUseCase(repository); }

    @Test
    public void should_throw_business_exception_when_id_not_exist_in_db() {
        byte[] uuid = Utils.getByteArrayFromGuid("123e4567-e89b-12d3-a456-556642440000");

        Assertions.assertThatCode(() -> {
            this.useCase.execute(uuid);
        }).isInstanceOf(BusinessException.class)
                .hasMessage("L'identifiant 123e4567-e89b-12d3-a456-556642440000 n'existe pas");
    }
}