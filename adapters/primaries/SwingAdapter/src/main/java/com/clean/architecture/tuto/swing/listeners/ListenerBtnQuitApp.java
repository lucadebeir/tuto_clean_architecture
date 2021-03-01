package com.clean.architecture.tuto.swing.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerBtnQuitApp implements ActionListener {

    public ListenerBtnQuitApp() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int responseUser = JOptionPane.showConfirmDialog(null, "Voulez-vous quitter le programme ?", "Quitter le programme", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (responseUser == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
