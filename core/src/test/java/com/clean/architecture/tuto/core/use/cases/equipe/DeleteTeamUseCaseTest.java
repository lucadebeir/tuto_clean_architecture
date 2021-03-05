package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DeleteTeamUseCaseTest {

    @Mock
    private RepositoryTeam repository;

    private DeleteTeamUseCase useCase;

    @Before
    public void setUp() {
        this.useCase = new DeleteTeamUseCase(repository);
    }

    @Test
    public void should_throw_business_exception_when_id_not_exist_in_db() throws TechnicalException, SQLException, UnknownHostException, BusinessException {
        Mockito.when(this.repository.findById("9"))
                .thenReturn(Optional.empty());
        this.useCase.execute("9");
    }
}