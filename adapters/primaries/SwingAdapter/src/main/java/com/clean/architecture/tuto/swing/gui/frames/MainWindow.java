package com.clean.architecture.tuto.swing.gui.frames;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.swing.gui.panels.HomePanel;

import javax.swing.*;

/**
 * Fenêtre principale de l'application
 */
public class MainWindow extends JFrame {

    private HomePanel homePanel;

    public MainWindow() {
        super("Tuto Clean Architecture");

        this.homePanel = new HomePanel(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setPreferredSize(new Dimension(1000,800));
        this.add(homePanel);

        this.pack();
        this.setVisible(true);
    }

    public void updatePanel(JPanel panel) {
        this.setContentPane(panel);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        this.pack();
    }

}
