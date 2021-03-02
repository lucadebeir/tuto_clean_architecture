package com.clean.architecture.tuto.swing.gui.panels.team;

import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.models.tables.DisplayAllPersons;
import com.clean.architecture.tuto.swing.gui.models.tables.DisplayAllTeams;
import com.clean.architecture.tuto.swing.listeners.ListenerBtnRoot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DisplayAllTeamsPanel extends JPanel {

    private MainWindow mainWindow;

    JPanel personsOfATeam;
    /**
     * Ce layout permet de disposer les widgets (inputs, labels, images, des panels aussi)
     * en colonne comme avec du flex (flex-direction) en CSS.
     */
    private BoxLayout layout;

    /**
     * Ce tableau sert à afficher toutes les personnes présentes dans la bd
     */
    private DisplayAllTeams allTeams;
    private JTable table;
    private JTable tablePersons;
    private JScrollPane scrollPane;
    private JScrollPane scrollPanePersons;


    public DisplayAllTeamsPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        this.layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(this.layout);
        this.personsOfATeam = new JPanel(new BorderLayout());

        this.allTeams = new DisplayAllTeams();
        this.table = new JTable(this.allTeams);
        this.tablePersons = new JTable();
        table.setAutoCreateRowSorter(true);

        table.addMouseListener(l);
        this.scrollPane = new JScrollPane(this.table);
        this.scrollPanePersons = new JScrollPane();

        refreshTablePersons();
    }

    MouseListener l = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            JTable table = (JTable) e.getComponent();
            int row = table.rowAtPoint(e.getPoint());
            tablePersons = new JTable(new DisplayAllPersons(allTeams.getTeams().get(row).getList()));
            scrollPanePersons = new JScrollPane(tablePersons);
            refreshTablePersons();
        }
    };

    private void refreshTablePersons() {
        this.personsOfATeam.removeAll();
        this.removeAll();

        this.add(new JLabel("Liste des équipes", SwingConstants.CENTER));
        this.add(this.scrollPane);
        this.add(new JLabel("Liste des membres de l'équipe", SwingConstants.CENTER));
        this.personsOfATeam.add(this.scrollPanePersons, BorderLayout.CENTER);

        this.add(this.personsOfATeam);

        this.revalidate();
        this.repaint();
    }
}
