package com.clean.architecture.tuto.swing.gui.panels;

import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.listeners.ListenerBtnRoot;

import javax.swing.*;

public class DisplayAllTeamsPanel extends JPanel {

    private MainWindow mainWindow;

    /**
     * Ce layout permet de disposer les widgets (inputs, labels, images, des panels aussi)
     * en colonne comme avec du flex (flex-direction) en CSS.
     */
    private BoxLayout layout;

    /**
     * Ce bouton sert à revenir au panel précédent
     */
    private JButton btnBack;

    private ListenerBtnRoot listenerBtnBack;

    public DisplayAllTeamsPanel(MainWindow mainWindow, JPanel panel) {
        this.mainWindow = mainWindow;

        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(this.layout);

        this.listenerBtnBack = new ListenerBtnRoot(mainWindow, panel);

        this.btnBack = new JButton("Retour");

        this.btnBack.addActionListener(this.listenerBtnBack);

        this.add(this.btnBack);
    }
}
