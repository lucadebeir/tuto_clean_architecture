package com.clean.architecture.tuto.core.ports.equipe;

import com.clean.architecture.tuto.core.models.Team;

import java.net.UnknownHostException;

public interface RepositoryTeam {

    Team create(Team team) throws UnknownHostException;
}
