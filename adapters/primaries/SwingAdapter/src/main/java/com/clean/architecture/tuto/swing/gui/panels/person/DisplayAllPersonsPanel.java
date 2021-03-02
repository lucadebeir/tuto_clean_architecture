package com.clean.architecture.tuto.swing.gui.panels.person;

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
     * Ce tableau sert à afficher toutes les personnes présentes dans la bd
     */
    private DisplayAllPersons allPersons;
    private JTable table;
    private JScrollPane scrollPane;

    /**
     *
     * @param mainWindow
     */
    public DisplayAllPersonsPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        this.layout = new BorderLayout();
        this.setLayout(this.layout);

        this.allPersons = new DisplayAllPersons();
        this.table = new JTable(this.allPersons);
        table.setAutoCreateRowSorter(true);
        this.scrollPane = new JScrollPane(this.table);

        this.add(new JLabel("Liste des personnes", SwingConstants.CENTER), BorderLayout.PAGE_START);
        this.add(this.scrollPane, BorderLayout.CENTER);
    }
}
