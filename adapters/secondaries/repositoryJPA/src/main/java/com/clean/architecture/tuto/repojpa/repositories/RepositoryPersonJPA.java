package com.clean.architecture.tuto.repojpa.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.ports.personne.RepositoryPerson;
import com.clean.architecture.tuto.repojpa.entities.PersonEntity;
import lombok.NoArgsConstructor;

import javax.persistence.TypedQuery;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class RepositoryPersonJPA extends AbstractRepositoryJPA implements RepositoryPerson {

    @Override
    public Person create(Person person) {
        try {
            em.getTransaction().begin();

            PersonEntity p = new PersonEntity();
            p.setFirstname(person.getFirstName());
            p.setLastname(person.getLastName());
            p.setAge(person.getAge());

            em.persist(p); // save

            person.setUuid(p.getUuid());

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        return person;
    }

    @Override
    public List<Person> getAll() {
        List<Person> allPerson = new ArrayList<>();
        try {
            em.getTransaction().begin();

            TypedQuery<PersonEntity> query = em.createQuery("SELECT p FROM PersonEntity p", PersonEntity.class);
            List<PersonEntity> personList = query.getResultList();

            personList.forEach(pp -> {
                allPerson.add(Person.builder()
                        .uuid(pp.getUuid())
                        .firstName(pp.getFirstname())
                        .lastName(pp.getLastname())
                        .age(pp.getAge())
                        .build());
            });

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return allPerson;
    }

    @Override
    public Optional<Person> findByUuid(byte[] uuid) {
        Person person = null;

        try {
            em.getTransaction().begin();

            TypedQuery<PersonEntity> query = em.createQuery("SELECT p FROM PersonEntity p WHERE p.uuid = :uuid ", PersonEntity.class);
            query.setParameter("uuid", uuid);
            PersonEntity pe = query.getSingleResult();

            person = Person.builder()
                    .uuid(uuid)
                    .firstName(pe.getFirstname())
                    .lastName(pe.getLastname())
                    .age(pe.getAge())
                    .build();

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        return Optional.ofNullable(person);

    }

    @Override
    public Person update(Person personToUpdate) throws UnknownHostException, SQLException {
        return null;
    }

    @Override
    public void deleteByUuid(byte[] uuid) throws SQLException {

    }

    @Override
    public boolean existsByUuidPerson(byte[] s) {
        return false;
    }
}
