package com.clean.architecture.tuto.cli.gui;

import com.clean.architecture.tuto.cli.enums.MainMenuEnum;

import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {

    public static final String[] VALUES = new String[] {"1","2","3","4","5","6", "7"};

    public static MainMenuEnum displayMainMenu(Scanner in) {
        String choice = "";
        try {

            System.out.println("-----------------------------------------------");
            System.out.println("              GESTION TUTO                     ");
            System.out.println("-----------------------------------------------");
            System.out.println("Que voulez-vous faire ?");
            System.out.println("1 - Afficher toutes les personnes");
            System.out.println("2 - Afficher les détails d'une personne");
            System.out.println("3 - Afficher toutes les équipes");
            System.out.println("4 - Afficher tous les détails d'une équipe");
            System.out.println("5 - Créer une personne");
            System.out.println("6 - Créer une équipe");
            System.out.println("7 - Quitter");

            while (!Arrays.asList(VALUES).contains(choice)) {
                System.out.println("Entrer le numéro de votre choix : ");
                choice = in.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return MainMenuEnum.getFromString(choice);
    }

}
