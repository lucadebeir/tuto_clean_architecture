package com.clean.architecture.tuto.cli.services;

import com.clean.architecture.tuto.cli.config.Config;
import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class PersonService {

    public void createPerson(Scanner in) throws InterruptedException {
        try {
            System.out.println("-----------------------------------------------");
            System.out.println("           CREATION D'UNE PERSONNE             ");
            System.out.println("-----------------------------------------------");
            System.out.println("Prénom : ");
            String firstname = in.nextLine();
            System.out.println("Nom : ");
            String lastname = in.nextLine();
            System.out.println("Age : ");
            String age = in.nextLine();
            Person newPerson = new Person(null, firstname, lastname, Integer.parseInt(age));
            newPerson = Config.getCreatePersonUseCase().execute(newPerson);
            System.out.println(newPerson.getFirstName() + " a l'identifiant " + newPerson.getId());
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
        System.out.println("      AFFICHAGE DE TOUTES LES PERSONNES        ");
        System.out.println("-----------------------------------------------");
        List<Person> list = Config.getAllPersonUseCase().execute();
        System.out.println("Identifiant | Prenom | Nom | Age");
        for(Person p: list) {
            System.out.println(p.getId() + " | " + p.getFirstName() + " | " + p.getLastName() + " | " + p.getAge());
        }
    }

    public void displayDetailsPerson(Scanner in) throws InterruptedException {
        try {
            System.out.println("-----------------------------------------------");
            System.out.println("     AFFICHAGE DES DETAILS D'UNE PERSONNE      ");
            System.out.println("-----------------------------------------------");
            System.out.println("Id : ");
            String id = in.nextLine();
            Person p = new Person(id, null, null, null);
            p = Config.getDisplayDetailsPersonUseCase().execute(p);
            System.out.println(p.getFirstName() + " " + p.getLastName() + " a " + p.getAge() + "ans");
        } catch (BusinessException e) {
            System.err.println(String.join(System.lineSeparator(), e.getErrorsList()));
            Thread.sleep(1500);
        } catch (TechnicalException e) {
            System.err.println("Erreur technique : Veuillez consulter le support technique");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

}
