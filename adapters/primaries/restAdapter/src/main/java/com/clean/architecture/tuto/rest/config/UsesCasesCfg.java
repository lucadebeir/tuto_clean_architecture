package com.clean.architecture.tuto.rest.config;

import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.clean.architecture.tuto.core.use.cases.personne.GetAllPersonUseCase;
import com.clean.architecture.tuto.reposql.repositories.RepositoryPersonMongoDB;
import com.clean.architecture.tuto.reposql.repositories.RepositoryPersonSQL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsesCasesCfg {

    @Bean
    public RepositoryPerson repositoryPerson() {
        return new RepositoryPersonSQL();
    }

    @Bean
    public GetAllPersonUseCase getAllPersonUseCase(RepositoryPerson repositoryPerson) {
        return new GetAllPersonUseCase(repositoryPerson);
    }

}
