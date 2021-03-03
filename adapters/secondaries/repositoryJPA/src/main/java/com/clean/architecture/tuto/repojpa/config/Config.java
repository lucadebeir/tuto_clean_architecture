package com.clean.architecture.tuto.repojpa.config;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Getter
@Setter
public class Config {

    public static class SingletonJPA {

        private static SingletonJPA instance;
        public EntityManagerFactory emf;

        private SingletonJPA() {
            this.emf = Persistence.createEntityManagerFactory("jpa-test") ;
        }

        public EntityManagerFactory getEmf() {
            return emf;
        }

        public static SingletonJPA getInstance() {
            if (instance == null) {
                instance = new SingletonJPA();
            }
            return instance;
        }
    }

}
