package com.clean.architecture.tuto.repojpa.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.core.ports.equipe.RepositoryTeam;
import com.clean.architecture.tuto.repojpa.entities.PersonEntity;
import com.clean.architecture.tuto.repojpa.entities.TeamEntity;
import lombok.NoArgsConstructor;

import javax.persistence.TypedQuery;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class RepositoryTeamJPA extends AbstractRepositoryJPA implements RepositoryTeam {

    @Override
    public Team create(Team team) {

        try {
            em.getTransaction().begin();

            TeamEntity t = new TeamEntity();
            t.setName(team.getName());
            team.getList().forEach(person -> {
                PersonEntity pe = new PersonEntity();
                pe.setId(Integer.parseInt(person.getId()));
                pe.setFirstname(person.getFirstName());
                pe.setLastname(person.getLastName());
                pe.setAge(person.getAge());
                t.addPerson(pe);
            });

            em.persist(t); // save

            team.setId(String.valueOf(t.getId()));

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        return team;
    }

    @Override
    public List<Team> getAll() {
        List<Team> allTeam = new ArrayList<>();
        try {
            em.getTransaction().begin();

            TypedQuery<TeamEntity> query = em.createQuery("SELECT t FROM TeamEntity t", TeamEntity.class);
            List<TeamEntity> teamEntityList = query.getResultList();

            teamEntityList.forEach(teamEntity -> {
                allTeam.add(Team.builder()
                        .id(String.valueOf(teamEntity.getId()))
                        .name(teamEntity.getName())
                        .list(getListPersonOfATeam(teamEntity))
                        .build());
            });

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return allTeam;
    }

    @Override
    public Optional<Team> findById(String id) {
        Team team = null;

        try {
            em.getTransaction().begin();

            TypedQuery<TeamEntity> query = em.createQuery("SELECT t FROM TeamEntity t WHERE t.id = :id ", TeamEntity.class);
            query.setParameter("id", Integer.parseInt(id));
            TeamEntity te = query.getSingleResult();

            team = Team.builder()
                    .id(id)
                    .name(te.getName())
                    .list(getListPersonOfATeam(te))
                    .build();

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        return Optional.ofNullable(team);
    }

    @Override
    public boolean existsByName(String name) {
        List<String> allTeam = new ArrayList<>();
        try {
            em.getTransaction().begin();
            TypedQuery<String> query = em.createQuery("select t.name FROM TeamEntity t", String.class);

            allTeam = query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        return allTeam.contains(name);
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Team update(Team team) throws UnknownHostException, SQLException {
        return null;
    }

    /**
     * Fonction qui retourne une liste de personnes appartenant à une entité Team
     * @param teamEntity
     * @return personList
     */
    public List<Person> getListPersonOfATeam(TeamEntity teamEntity) {
        List<Person> personList = new ArrayList<>();
        teamEntity.getPersonsList().forEach(personEntity -> {
            personList.add(Person.builder()
                    .id(String.valueOf(personEntity.getId()))
                    .firstName(personEntity.getFirstname())
                    .lastName(personEntity.getLastname())
                    .age(personEntity.getAge())
                    .build());
        });
        return personList;
    }
}
