package com.clean.architecture.tuto.swing.listeners;

import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.panels.MainTeamsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerBtnRoot implements ActionListener {

    private MainWindow mainWindow;

    private JPanel panel;

    public ListenerBtnRoot(MainWindow mainWindow, JPanel panel) {
        this.mainWindow = mainWindow;
        this.panel = panel;
    }

    public void actionPerformed(ActionEvent e) {
        this.mainWindow.updatePanel(this.panel);
    }

}
