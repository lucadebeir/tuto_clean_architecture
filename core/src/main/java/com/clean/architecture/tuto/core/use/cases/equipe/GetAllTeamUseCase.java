package com.clean.architecture.tuto.core.use.cases.equipe;

import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import lombok.AllArgsConstructor;

import java.net.UnknownHostException;
import java.util.List;

@AllArgsConstructor
public class GetAllTeamUseCase {

    private RepositoryTeam repository; //interface

    public List<Team> execute() throws UnknownHostException {
        return repository.getAll();
    }
}
