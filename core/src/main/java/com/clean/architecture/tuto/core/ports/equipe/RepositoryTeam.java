package com.clean.architecture.tuto.core.ports.equipe;

import com.clean.architecture.tuto.core.models.Team;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

public interface RepositoryTeam {

    Team create(Team team) throws UnknownHostException;

    List<Team> getAll() throws UnknownHostException;

    Optional<Team> findById(String id) throws UnknownHostException;

    boolean existsByName(String name) throws UnknownHostException;

}
