package com.clean.architecture.tuto.cli.config;

import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.clean.architecture.tuto.core.use.cases.equipe.CreateTeamUseCase;
import com.clean.architecture.tuto.core.use.cases.personne.CreatePersonUseCase;
import com.clean.architecture.tuto.core.use.cases.personne.DisplayDetailsPersonUseCase;
import com.clean.architecture.tuto.core.use.cases.personne.GetAllPersonUseCase;
import com.clean.architecture.tuto.reposql.repositories.RepositoryPersonMongoDB;
import com.clean.architecture.tuto.reposql.repositories.RepositoryPersonSQL;
import com.clean.architecture.tuto.reposql.repositories.RepositoryTeamMongoDB;
import com.clean.architecture.tuto.reposql.repositories.RepositoryTeamSQL;

public class Config {

    //PERSON
    private static RepositoryPerson repositoryPersonSQL = new RepositoryPersonSQL();
    private static RepositoryPerson repositoryPersonMongoDB = new RepositoryPersonMongoDB();

    public static CreatePersonUseCase getCreatePersonUseCase() {
        return new CreatePersonUseCase(repositoryPersonMongoDB);
    }

    public static GetAllPersonUseCase getAllPersonUseCase() { return new GetAllPersonUseCase(repositoryPersonMongoDB); }

    public static DisplayDetailsPersonUseCase getDisplayDetailsPersonUseCase() { return new DisplayDetailsPersonUseCase(repositoryPersonMongoDB); }


    //TEAM
    private static RepositoryTeam repositoryTeamSQL = new RepositoryTeamSQL();
    private static RepositoryTeam repositoryTeamMongoDB = new RepositoryTeamMongoDB();

    public static CreateTeamUseCase getCreateTeamUseCase() {
        return new CreateTeamUseCase(repositoryTeamMongoDB);
    }

}
