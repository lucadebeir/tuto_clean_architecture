package com.clean.architecture.tuto.core.ports.equipe;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Team;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RepositoryTeam {

    Team create(Team team) throws UnknownHostException, SQLException;

    List<Team> getAll() throws UnknownHostException, SQLException;

    Optional<Team> findByUuid(String uuid) throws BusinessException, UnknownHostException, TechnicalException, SQLException;

    boolean existsByName(String name) throws UnknownHostException, SQLException;

    void deleteByUuid(String uuid);

    Team update(Team team) throws UnknownHostException, SQLException;
}
