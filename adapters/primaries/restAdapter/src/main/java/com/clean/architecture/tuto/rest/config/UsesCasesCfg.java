package com.clean.architecture.tuto.rest.config;

import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.clean.architecture.tuto.core.use.cases.equipe.CreateTeamUseCase;
import com.clean.architecture.tuto.core.use.cases.equipe.DisplayDetailsTeamUseCase;
import com.clean.architecture.tuto.core.use.cases.equipe.GetAllTeamUseCase;
import com.clean.architecture.tuto.core.use.cases.personne.CreatePersonUseCase;
import com.clean.architecture.tuto.core.use.cases.personne.DisplayDetailsPersonUseCase;
import com.clean.architecture.tuto.core.use.cases.personne.GetAllPersonUseCase;
import com.clean.architecture.tuto.repomongodb.repositories.RepositoryPersonMongoDB;
import com.clean.architecture.tuto.repomongodb.repositories.RepositoryTeamMongoDB;
import com.clean.architecture.tuto.reposql.repositories.RepositoryPersonSQL;
import com.clean.architecture.tuto.reposql.repositories.RepositoryTeamSQL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UsesCasesCfg {

    @Bean
    public RepositoryPerson repositoryPerson()  {
        return new RepositoryPersonMongoDB();
    }
    @Bean
    public RepositoryTeam repositoryTeam()  {
        return new RepositoryTeamMongoDB();
    }

    //PERSON
    @Bean
    public GetAllPersonUseCase getAllPersonUseCase(RepositoryPerson repositoryPerson) {
        return new GetAllPersonUseCase(repositoryPerson);
    }

    @Bean
    public DisplayDetailsPersonUseCase displayDetailsPersonUseCase(RepositoryPerson repositoryPerson) {
        return new DisplayDetailsPersonUseCase(repositoryPerson);
    }

    @Bean
    public CreatePersonUseCase createPersonUseCase(RepositoryPerson repositoryPerson) {
        return new CreatePersonUseCase(repositoryPerson);
    }

    //TEAM
    @Bean
    public CreateTeamUseCase createTeamUseCase(RepositoryTeam repositoryTeam) {
        return new CreateTeamUseCase(repositoryTeam);
    }

    @Bean
    public DisplayDetailsTeamUseCase findByIdTeamUseCase(RepositoryTeam repositoryTeam) {
        return new DisplayDetailsTeamUseCase(repositoryTeam);
    }

    @Bean
    public GetAllTeamUseCase getAllTeamUseCase(RepositoryTeam repositoryTeam) {
        return new GetAllTeamUseCase(repositoryTeam);
    }

}
