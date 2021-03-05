package com.clean.architecture.tuto.swing.config;

import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.clean.architecture.tuto.core.use.cases.equipe.CreateTeamUseCase;
import com.clean.architecture.tuto.core.use.cases.equipe.DisplayDetailsTeamUseCase;
import com.clean.architecture.tuto.core.use.cases.equipe.GetAllTeamUseCase;
import com.clean.architecture.tuto.core.use.cases.personne.*;
import com.clean.architecture.tuto.repomongodb.repositories.RepositoryPersonMongoDB;
import com.clean.architecture.tuto.repomongodb.repositories.RepositoryTeamMongoDB;
import com.clean.architecture.tuto.reposql.repositories.RepositoryPersonSQL;
import com.clean.architecture.tuto.reposql.repositories.RepositoryTeamSQL;

public class Config {

    //PERSON
    private static RepositoryPerson repositoryPersonSQL = new RepositoryPersonSQL();
    private static RepositoryPerson repositoryPersonMongoDB = new RepositoryPersonMongoDB();

    public static CreatePersonUseCase getCreatePersonUseCase() {
        return new CreatePersonUseCase(repositoryPersonSQL);
    }

    public static GetAllPersonUseCase getAllPersonUseCase() { return new GetAllPersonUseCase(repositoryPersonSQL); }

    public static DisplayDetailsPersonUseCase findByIdPersonUseCase() { return new DisplayDetailsPersonUseCase(repositoryPersonSQL); }

    public static UpdatePersonUseCase updatePersonUseCase() { return new UpdatePersonUseCase(repositoryPersonSQL); }

    public static DeletePersonUseCase deletePersonUseCase() { return new DeletePersonUseCase(repositoryPersonSQL); }

    //TEAM
    private static RepositoryTeam repositoryTeamSQL = new RepositoryTeamSQL();
    private static RepositoryTeam repositoryTeamMongoDB = new RepositoryTeamMongoDB();

    public static CreateTeamUseCase getCreateTeamUseCase() {
        return new CreateTeamUseCase(repositoryTeamSQL);
    }

    public static GetAllTeamUseCase getAllTeamUseCase() { return new GetAllTeamUseCase(repositoryTeamSQL); }

    public static DisplayDetailsTeamUseCase findByIdTeamUseCase() { return new DisplayDetailsTeamUseCase(repositoryTeamSQL); }
}
