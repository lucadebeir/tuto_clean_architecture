package com.clean.architecture.tuto.repojpa.repositories;

import com.clean.architecture.tuto.repojpa.entities.PersonEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test") ;
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            PersonEntity p = new PersonEntity();
           /* p.setAge(19);
            p.setFirstname("Pop");
            p.setLastname("Pap");*/

            //em.persist(p); // save


            /*p.setId(145);
            p.setAge(19);
            p.setFirstname("Zombie");
            p.setLastname("Il y'a un entretien ?!");
            em.merge(p); // update */


            TypedQuery<PersonEntity> query = em.createQuery("SELECT p FROM PersonEntity p", PersonEntity.class);
            List<PersonEntity> personList = query.getResultList();

            personList.forEach(pp -> System.out.println(pp.getId()));

            query = em.createQuery("SELECT p FROM PersonEntity p WHERE p.id = 145 ", PersonEntity.class);
            PersonEntity pe = query.getSingleResult();

            System.out.println(pe.getFirstname());

            /*query = em.createQuery("SELECT p FROM PersonEntity p WHERE p.id = :id ", PersonEntity.class);
            query.setParameter("id", 1);
            pe = query.getSingleResult();

            System.out.println(pe.getFirstname());

            em.remove(pe);*/

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }

}
