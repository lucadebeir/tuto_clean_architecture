package com.clean.architecture.tuto.swing.gui.panels;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.listeners.ListenerBtnRoot;

import javax.swing.*;

public class MainPersonsPanel extends JPanel {

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
     * Ce bouton sert à créer une personne
     */
    private JButton btnCreate;

    /**
     * Ce bouton sert à afficher les personnes
     */
    private JButton btnDisplayAll;

    /**
     * Ce bouton sert à revenir au panel HomePanel
     */
    private JButton btnBackToHomePanel;

    private ListenerBtnRoot listenerBtnBack;
    private ListenerBtnRoot listenerBtnDisplayAllPersons;
    private ListenerBtnRoot listenerBtnCreatePerson;

    public MainPersonsPanel(MainWindow mainWindow, JPanel panel) {
        this.mainWindow = mainWindow;

        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(this.layout);

        this.listenerBtnBack = new ListenerBtnRoot(mainWindow, panel);
        this.listenerBtnDisplayAllPersons = new ListenerBtnRoot(mainWindow, new DisplayAllPersonsPanel(mainWindow, this));
        this.listenerBtnCreatePerson = new ListenerBtnRoot(mainWindow, new CreatePersonPanel(mainWindow, this));

        this.btnCreate = new JButton("Créer une personne");
        this.btnDisplayAll = new JButton("Lister les personnes");
        this.btnBackToHomePanel = new JButton("Retour");

        this.btnBackToHomePanel.addActionListener(this.listenerBtnBack);
        this.btnDisplayAll.addActionListener(this.listenerBtnDisplayAllPersons);
        this.btnCreate.addActionListener(this.listenerBtnCreatePerson);

        this.add(this.btnCreate);
        this.add(this.btnDisplayAll);
        this.add(this.btnBackToHomePanel);
    }
}
