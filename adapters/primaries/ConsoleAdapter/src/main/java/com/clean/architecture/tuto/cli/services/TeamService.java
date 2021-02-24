package com.clean.architecture.tuto.cli.services;

import com.clean.architecture.tuto.cli.config.Config;
import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeamService {

    public void create(Scanner in) throws InterruptedException {
        try {
            System.out.println("-----------------------------------------------");
            System.out.println("            CREATION D'UNE EQUIPE              ");
            System.out.println("-----------------------------------------------");
            System.out.println("Nom : ");
            String name = in.nextLine();
            Team newTeam = Team.builder().name(name).build();

            System.out.println("Vous allez rentrer les identifiants des personnes rejoignant cette équipe.");
            System.out.println("Séparer les identifiants par une virgule.");
            System.out.println("Par exemple : ");
            System.out.println("1,2,3,4,5,6");
            System.out.println(" ");
            System.out.println("Entrer les identifiants des personnes rejoignant cette équipe : ");

            List<Person> personList = new ArrayList<>();

            String id = in.nextLine();
            if(!id.isEmpty()) {
                String[] list = id.split(",");

                for(String element: list) {
                    Person p = new Person(element, null, null, null);
                    p = Config.getDisplayDetailsPersonUseCase().execute(p);
                    personList.add(p);
                }
            }

            newTeam.setList(personList);
            newTeam = Config.getCreateTeamUseCase().execute(newTeam);

            System.out.println(newTeam.getName() + " a l'identifiant " + newTeam.getId());
        } catch (BusinessException e) {
            System.err.println(String.join(System.lineSeparator(), e.getErrorsList()));
            Thread.sleep(1500);
        } catch (TechnicalException e) {
            System.err.println("Erreur technique : Veuillez consulter le support technique");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void findAll() throws UnknownHostException {
        System.out.println("-----------------------------------------------");
        System.out.println("       AFFICHAGE DE TOUTES LES EQUIPES         ");
        System.out.println("-----------------------------------------------");
        List<Team> list = Config.getAllTeamUseCase().execute();
        System.out.println("Identifiant | Nom de l'équipe");
        for(Team t: list) {
            System.out.println(t.getId() + " | " + t.getName());
            System.out.println("Identifiant | Prenom | Nom | Age");
            for(Person p: t.getList()) {
                System.out.println(p.getId() + " | " + p.getFirstName() + " | " + p.getLastName() + " | " + p.getAge());
            }
            System.out.println("-----------------------------------------------");
        }
    }

}
