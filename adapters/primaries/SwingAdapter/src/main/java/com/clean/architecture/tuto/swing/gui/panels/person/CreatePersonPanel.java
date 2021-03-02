package com.clean.architecture.tuto.swing.gui.panels.person;

import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.utils.UtilsIcons;
import com.clean.architecture.tuto.swing.listeners.CreatePersonListener;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

@Getter
@Setter
public class CreatePersonPanel extends JPanel {

    /**
     * Lien direct vers notre fenêtre principale qui affiche le panel principal
     */
    private MainWindow mainWindow;

    /**
     * Ce bouton sert à créer une personne
     */
    private JButton btnCreate;

    /**
     * Ce panel sert à la création d'une personne
     */
    JPanel form;
    JTextField firstname;
    JTextField lastname;
    JFormattedTextField age;

    /**
     * Listener pour valider une création d'une personne
     */
    private CreatePersonListener listenerBtnCreatePerson;

    private UtilsIcons utilsIcons = new UtilsIcons();

    public CreatePersonPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        this.btnCreate = new JButton("Créer une nouvelle personne", utilsIcons.resizeImage("/images/add_user_icon.png"));

        form = initForm();
        this.listenerBtnCreatePerson = new CreatePersonListener(mainWindow, this);
        this.btnCreate.addActionListener(this.listenerBtnCreatePerson);

        this.add(btnCreate);
    }

    public JPanel initForm() {
        form = new JPanel(new GridLayout(0,1));
        firstname = new JTextField(10);
        lastname = new JTextField(10);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        age = new JFormattedTextField(formatter);

        //FORM

        JLabel labelFirstname = new JLabel("Prénom : ", SwingConstants.CENTER);
        form.add(labelFirstname);
        labelFirstname.setLabelFor(firstname);
        form.add(firstname);
        JLabel labelLastname = new JLabel("Nom : ", SwingConstants.CENTER);
        form.add(labelLastname);
        labelLastname.setLabelFor(lastname);
        form.add(lastname);
        JLabel labelAge = new JLabel("Age : ", SwingConstants.CENTER);
        form.add(labelAge);
        labelAge.setLabelFor(age);
        form.add(age);

        return form;
    }

}
