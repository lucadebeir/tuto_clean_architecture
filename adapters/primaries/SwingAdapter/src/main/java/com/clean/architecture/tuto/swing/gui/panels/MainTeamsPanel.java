package com.clean.architecture.tuto.swing.gui.panels;

import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.listeners.ListenerBtnRoot;

import javax.swing.*;

public class MainTeamsPanel extends JPanel {

    /**
     * Lien direct vers notre fenêtre principale qui affiche le panel principal
     */
    private MainWindow mainWindow;

    /**
     * Ce layout permet de disposer les widgets (inputs, labels, images, des panels aussi)
     * en colonne comme avec du flex (flex-direction) en CSS.
     */
    private BoxLayout layout;

    /**
     * Ce bouton sert à créer une équipe
     */
    private JButton btnCreate;

    /**
     * Ce bouton sert à afficher toutes les équipes
     */
    private JButton btnDisplayAll;

    /**
     * Ce bouton sert à revenir au panel HomePanel
     */
    private JButton btnBackToHomePanel;

    private ListenerBtnRoot listenerBtnBack;
    private ListenerBtnRoot listenerBtnDisplayAllTeams;

    public MainTeamsPanel(MainWindow mainWindow, JPanel panel) {
        this.mainWindow = mainWindow;

        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(this.layout);

        this.listenerBtnBack = new ListenerBtnRoot(mainWindow, panel);
        this.listenerBtnDisplayAllTeams = new ListenerBtnRoot(mainWindow, new DisplayAllTeamsPanel(mainWindow, this));

        this.btnCreate = new JButton("Créer une équipe");
        this.btnDisplayAll = new JButton("Lister les équipes");
        this.btnBackToHomePanel = new JButton("Retour");

        this.btnBackToHomePanel.addActionListener(this.listenerBtnBack);
        this.btnDisplayAll.addActionListener(this.listenerBtnDisplayAllTeams);

        this.add(this.btnCreate);
        this.add(this.btnDisplayAll);
        this.add(this.btnBackToHomePanel);
    }


}
