package com.clean.architecture.tuto.swing.gui.panels;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.swing.config.Config;
import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.listeners.ListenerBtnRoot;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class CreatePersonPanel extends JPanel {

    /**
     * Lien direct vers notre fenêtre principale qui affiche le panel principal
     */
    private MainWindow mainWindow;

    /**
     * Ce layout permet de disposer les widgets (inputs, labels, images, des panels aussi)
     * en colonne comme avec du flex (flex-direction) en CSS.
     */
    private BorderLayout layout;

    /**
     * Ce bouton sert à créer une personne
     */
    private JButton btnCreate;

    /**
     * Ce panel sert à la création d'une personne
     */
    private JPanel form = new JPanel(new GridLayout(0, 1));
    JTextField firstname = new JTextField();
    JTextField lastname = new JTextField();

    JFormattedTextField age;

    /**
     * Ce bouton sert à revenir au panel HomePanel
     */
    private JButton btnBack;

    private ListenerBtnRoot listenerBtnBack;
    private CreatePersonListener listenerBtnCreatePerson;

    public CreatePersonPanel(MainWindow mainWindow, JPanel panel) {
        super();

        this.mainWindow = mainWindow;

        this.layout = new BorderLayout();
        this.setLayout(this.layout);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        age = new JFormattedTextField(formatter);

        this.btnCreate = new JButton("Valider");
        this.listenerBtnCreatePerson = new CreatePersonListener();
        this.btnCreate.addActionListener(this.listenerBtnCreatePerson);
        this.btnBack = new JButton("Retour");
        this.listenerBtnBack = new ListenerBtnRoot(mainWindow, panel);
        this.btnBack.addActionListener(this.listenerBtnBack);

        this.add(this.btnBack, BorderLayout.PAGE_END);

        //FORM

        form.add(new JLabel("Prénom : "));
        form.add(firstname);
        form.add(new JLabel("Nom : "));
        form.add(lastname);
        form.add(new JLabel("Age : "));
        form.add(age);
        form.add(this.btnCreate);

        this.add(form, BorderLayout.CENTER);
    }

    public class CreatePersonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Config.getCreatePersonUseCase().execute(Person.builder()
                        .firstName(firstname.getText())
                        .lastName(lastname.getText())
                        .age((Integer) age.getValue())
                        .build());
            } catch (BusinessException ex) {
                JOptionPane.showConfirmDialog(null, ex.getMessage().split(","), "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (TechnicalException ex) {
                JOptionPane.showConfirmDialog(null, "Erreur technique : Veuillez contacter le service technique", "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
