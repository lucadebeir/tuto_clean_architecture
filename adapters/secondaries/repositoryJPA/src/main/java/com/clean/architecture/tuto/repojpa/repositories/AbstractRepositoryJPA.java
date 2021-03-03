package com.clean.architecture.tuto.repojpa.repositories;

import com.clean.architecture.tuto.repojpa.config.Config;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;

@Getter
@Setter
public abstract class AbstractRepositoryJPA {

    protected EntityManager em;

    public AbstractRepositoryJPA() {
        this.em = Config.SingletonJPA.getInstance().getEmf().createEntityManager();
    }
}
