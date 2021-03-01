package com.clean.architecture.tuto.swing;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.swing.gui.frames.MainWindow;

public class Application {

    private static void createAndShowGUI() {
        MainWindow mainWindow = new MainWindow();
    }

    public static void main(String[] args) {
        // Gestion des threads :
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
