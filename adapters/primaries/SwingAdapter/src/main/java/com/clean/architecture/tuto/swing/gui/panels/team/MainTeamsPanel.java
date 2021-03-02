package com.clean.architecture.tuto.swing.gui.panels.team;

import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.utils.UtilsIcons;
import com.clean.architecture.tuto.swing.listeners.ListenerBtnRoot;

import javax.swing.*;
import java.awt.*;

public class MainTeamsPanel extends JPanel {

    /**
     * Lien direct vers notre fenêtre principale qui affiche le panel principal
     */
    private MainWindow mainWindow;

    /**
     * Panel pour afficher toutes les équipes
     */
    private DisplayAllTeamsPanel displayAllTeamsPanel;

    /**
     * Panel pour créer une équipe
     */
    private CreateTeamPanel createTeamPanel;

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
    private ListenerBtnRoot listenerBtnBack;

    private UtilsIcons utilsIcons = new UtilsIcons();

    public MainTeamsPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.displayAllTeamsPanel = new DisplayAllTeamsPanel(mainWindow);
        this.createTeamPanel = new CreateTeamPanel(mainWindow);
        this.footer = new JPanel(new FlowLayout());

        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(this.layout);

        this.listenerBtnBack = new ListenerBtnRoot(mainWindow, "HOME");

        this.btnBackToHomePanel = new JButton("Retour", utilsIcons.resizeImage("/images/back_icon.png"));

        this.btnBackToHomePanel.addActionListener(this.listenerBtnBack);

        this.add(displayAllTeamsPanel);
        this.add(createTeamPanel);

        this.footer.add(this.btnBackToHomePanel);
        this.add(this.footer);
    }


}
