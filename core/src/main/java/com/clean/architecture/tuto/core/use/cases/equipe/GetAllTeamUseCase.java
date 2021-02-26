package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import lombok.AllArgsConstructor;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class GetAllTeamUseCase {

    private RepositoryTeam repository; //interface

    public List<Team> execute() throws TechnicalException {
        try {
            return repository.getAll();
        } catch (UnknownHostException | SQLException e) {
            e.printStackTrace();
            throw new TechnicalException(e.getMessage());
        }
    }
}
