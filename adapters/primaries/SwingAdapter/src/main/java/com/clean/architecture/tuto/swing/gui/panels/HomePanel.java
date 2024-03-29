package com.clean.architecture.tuto.swing.gui.panels;

import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.utils.UtilsIcons;
import com.clean.architecture.tuto.swing.listeners.ListenerBtnRoot;
import com.clean.architecture.tuto.swing.listeners.ListenerBtnQuitApp;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    /**
     * Lien direct vers notre fenêtre principale qui affiche le panel principal
     */
    private MainWindow mainWindow;

    /**
     * Ce layout permet de disposer les widgets (inputs, labels, images, des panels aussi)
     * en ligne comme avec du flex en CSS.
     */
    private FlowLayout layout;

    /**
     * Ce bouton sert à gérer les équipes
     */
    private JButton btnManageTeams;

    /**
     * Ce bouton sert à gérer les personnes
     */
    private JButton btnManagePersons;

    /**
     * Ce bouton sert à quitter l'application
     */
    private JButton btnQuit;

    private ListenerBtnRoot listenerBtnRootTeams;
    private ListenerBtnRoot listenerBtnRootPersons;

    private ListenerBtnQuitApp listenerBtnQuitApp;

    private UtilsIcons utilsIcons = new UtilsIcons();

    public HomePanel(MainWindow mainWindow) {

        this.mainWindow = mainWindow;

        this.layout = new FlowLayout();
        this.setLayout(this.layout);

        this.listenerBtnRootTeams = new ListenerBtnRoot(mainWindow, "MAIN_TEAMS");
        this.listenerBtnRootPersons = new ListenerBtnRoot(mainWindow, "MAIN_PERSONS");
        this.listenerBtnQuitApp = new ListenerBtnQuitApp();

        this.btnManageTeams = new JButton("Gérer les équipes", utilsIcons.resizeImage("/images/team_icon.png"));
        this.btnManagePersons = new JButton("Gérer les personnes", utilsIcons.resizeImage("/images/user_icon.png"));
        this.btnQuit = new JButton("Quitter", utilsIcons.resizeImage("/images/quit_icon.png"));


        this.btnManageTeams.addActionListener(this.listenerBtnRootTeams);
        this.btnManagePersons.addActionListener(this.listenerBtnRootPersons);
        this.btnQuit.addActionListener(this.listenerBtnQuitApp);

        this.add(this.btnManageTeams);
        this.add(this.btnManagePersons);
        this.add(this.btnQuit);
    }

}
