package com.clean.architecture.tuto.swing.gui.panels;

import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.models.tables.DisplayAllPersons;
import com.clean.architecture.tuto.swing.listeners.ListenerBtnRoot;

import javax.swing.*;
import java.awt.*;

public class DisplayAllPersonsPanel extends JPanel {

    private MainWindow mainWindow;

    /**
     * Ce layout permet de disposer les widgets (inputs, labels, images, des panels aussi)
     * en colonne comme avec du flex (flex-direction) en CSS.
     */
    private BorderLayout layout;

    /**
     * Ce bouton sert à revenir au panel précédent
     */
    private JButton btnBack;

    private ListenerBtnRoot listenerBtnBack;

    /**
     * Ce tableau sert à afficher toutes les personnes présentes dans la bd
     */
    private DisplayAllPersons allPersons;
    private JTable table;
    private JScrollPane scrollPane;

    /**
     *
     * @param mainWindow
     * @param panel
     */
    public DisplayAllPersonsPanel(MainWindow mainWindow, JPanel panel) {
        this.mainWindow = mainWindow;

        this.layout = new BorderLayout();
        this.setLayout(this.layout);

        this.allPersons = new DisplayAllPersons();
        this.table = new JTable(this.allPersons);
        table.setAutoCreateRowSorter(true);
        this.scrollPane = new JScrollPane(this.table);

        this.listenerBtnBack = new ListenerBtnRoot(mainWindow, panel);
        this.btnBack = new JButton("Retour");
        this.btnBack.addActionListener(this.listenerBtnBack);

        this.add(this.scrollPane, BorderLayout.CENTER);
        this.add(this.btnBack, BorderLayout.PAGE_END);
    }
}
