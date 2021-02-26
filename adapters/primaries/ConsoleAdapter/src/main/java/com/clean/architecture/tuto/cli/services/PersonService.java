package com.clean.architecture.tuto.cli.services;

import com.clean.architecture.tuto.cli.config.Config;
import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.clean.architecture.tuto.cli.constantes.ConstApp.ERROR_TECHNICAL;

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
            System.err.println(ERROR_TECHNICAL);
        }
    }

    public void findAll() {
        System.out.println("-----------------------------------------------");
        System.out.println("      AFFICHAGE DE TOUTES LES PERSONNES        ");
        System.out.println("-----------------------------------------------");
        List<Person> list = null;
        try {
            list = Config.getAllPersonUseCase().execute();
            System.out.println("Identifiant | Prenom | Nom | Age");
            list.forEach(p -> System.out.println(p.getId() + " | " + p.getFirstName() + " | " + p.getLastName() + " | " + p.getAge()));
        } catch (TechnicalException e) {
            System.err.println(ERROR_TECHNICAL);
        }

    }

    public void displayDetailsPerson(Scanner in) throws InterruptedException {
        try {
            System.out.println("-----------------------------------------------");
            System.out.println("     AFFICHAGE DES DETAILS D'UNE PERSONNE      ");
            System.out.println("-----------------------------------------------");
            // TODO : GuiUtils.displayTitle("AFFICHAGE DES DETAILS D'UNE PERSONNE");
            System.out.println("Id : ");
            String id = in.nextLine();
            Optional<Person> optionalPerson = Config.findByIdPersonUseCase().execute(id);
            optionalPerson.ifPresent(person -> System.out.println(person.getFirstName() + " " + person.getLastName() + " a " + person.getAge() + "ans"));
        } catch (BusinessException e) {
            System.err.println(String.join(System.lineSeparator(), e.getErrorsList()));
            Thread.sleep(1500);
        } catch (Exception te) {
            System.err.println(ERROR_TECHNICAL);
        }

    }

}
