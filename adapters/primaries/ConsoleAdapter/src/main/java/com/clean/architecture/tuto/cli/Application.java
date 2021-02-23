package com.clean.architecture.tuto.cli;

import com.clean.architecture.tuto.cli.services.PersonService;
import com.clean.architecture.tuto.cli.services.TeamService;

import java.net.UnknownHostException;
import java.util.Scanner;

import static com.clean.architecture.tuto.cli.gui.MainMenu.displayMainMenu;

public class Application {

    private static PersonService personService = new PersonService();
    private static TeamService teamService = new TeamService();

    public static void main(String[] args) throws InterruptedException {

        Scanner in = new Scanner(System.in);

        boolean keepGoing = true;
        while(keepGoing) {
            try {
                switch(displayMainMenu(in)) {
                    case CREATE_PERSON:
                        personService.createPerson(in);
                        break;
                    case CREATE_TEAM:
                        teamService.create(in);
                        break;
                    case DISPLAY_ALL_PERSONS:
                        personService.findAll();
                        break;
                    case DISPLAY_DETAILS_PERSON:
                        personService.displayDetailsPerson(in);
                        break;
                    case QUIT:
                        keepGoing = false;
                        break;
                }
                Thread.sleep(1500);
            } catch (UnknownHostException e) {
                System.err.println("Base de données inconnue, veuillez contacter l'équipe technique.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
