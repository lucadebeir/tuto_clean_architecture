package com.clean.architecture.tuto.swing.listeners;

import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.panels.HomePanel;
import com.clean.architecture.tuto.swing.gui.panels.person.MainPersonsPanel;
import com.clean.architecture.tuto.swing.gui.panels.team.MainTeamsPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerBtnRoot implements ActionListener {

    private MainWindow mainWindow;

    private String panel;

    public ListenerBtnRoot(MainWindow mainWindow, String panel) {
        this.mainWindow = mainWindow;
        this.panel = panel;
    }

    public void actionPerformed(ActionEvent e) {
        switch (panel) {
            case "HOME":
                this.mainWindow.updatePanel(new HomePanel(mainWindow));
                break;
            case "MAIN_PERSONS":
                this.mainWindow.updatePanel(new MainPersonsPanel(mainWindow));
                break;
            case "MAIN_TEAMS":
                this.mainWindow.updatePanel(new MainTeamsPanel(mainWindow));
                break;
            default:
                this.mainWindow.updatePanel(new HomePanel(mainWindow));
                break;
        }
    }

}
