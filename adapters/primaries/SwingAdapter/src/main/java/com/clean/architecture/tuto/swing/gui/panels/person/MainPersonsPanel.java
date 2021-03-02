package com.clean.architecture.tuto.swing.gui.panels.person;

import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.utils.UtilsIcons;
import com.clean.architecture.tuto.swing.listeners.ListenerBtnRoot;

import javax.swing.*;
import java.awt.*;

public class MainPersonsPanel extends JPanel {

    /**
     * Lien direct vers notre fenêtre principale qui affiche le panel principal
     */
    private MainWindow mainWindow;

    /**
     * Panel pour afficher toutes les personnes
     */
    private DisplayAllPersonsPanel displayAllPersonsPanel;

    /**
     * Panel pour créer une personne
     */
    private CreatePersonPanel createPersonPanel;

    /**
     * Footer
     */
    private JPanel footer;

    /**
     * Ce layout permet de disposer les widgets (inputs, labels, images, des panels aussi)
     * en colonne comme avec du flex (flex-direction) en CSS.
     */
    private BoxLayout layout;

    /**
     * Ce bouton sert à revenir au panel HomePanel
     */
    private JButton btnBackToHomePanel;
    private JButton create;

    /**
     * Listener pour revenir à la page précédente
     */
    private ListenerBtnRoot listenerBtnBack;

    private UtilsIcons utilsIcons = new UtilsIcons();

    /**
     * Constructeur
     * @param mainWindow
     */
    public MainPersonsPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(this.layout);

        this.displayAllPersonsPanel = new DisplayAllPersonsPanel(mainWindow);
        this.createPersonPanel = new CreatePersonPanel(mainWindow);
        this.footer = new JPanel(new FlowLayout());

        this.listenerBtnBack = new ListenerBtnRoot(mainWindow, "HOME");
        this.btnBackToHomePanel = new JButton("Retour", utilsIcons.resizeImage("/images/back_icon.png"));
        this.btnBackToHomePanel.addActionListener(this.listenerBtnBack);

        this.add(this.displayAllPersonsPanel);
        this.add(this.createPersonPanel);

        this.footer.add(this.btnBackToHomePanel, CENTER_ALIGNMENT);
        this.add(this.footer);
    }
}
