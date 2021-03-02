package com.clean.architecture.tuto.swing.gui.panels.team;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.swing.config.Config;
import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.models.checkbox.JCheckBoxWithPerson;
import com.clean.architecture.tuto.swing.gui.utils.UtilsIcons;
import com.clean.architecture.tuto.swing.listeners.CheckPersonListener;
import com.clean.architecture.tuto.swing.listeners.CreateTeamListener;
import lombok.Getter;
import lombok.Setter;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateTeamPanel extends JPanel {

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
    JTextField teamName;

    /**
     * Listener pour valider une création d'une personne
     */
    private CreateTeamListener listenerBtnCreateTeam;

    /* This list will store selected ids */
    List<Person> selectPersons = new ArrayList<>();

    ItemListener itemListener = new CheckPersonListener(this);

    private UtilsIcons utilsIcons = new UtilsIcons();

    public CreateTeamPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        form = initForm();

        this.btnCreate = new JButton("Créer une nouvelle équipe", utilsIcons.resizeImage("/images/add_team_icon.png"));
        this.listenerBtnCreateTeam = new CreateTeamListener(mainWindow,this);
        this.btnCreate.addActionListener(this.listenerBtnCreateTeam);

        this.add(btnCreate);
    }

    public JPanel initForm() {
        form = new JPanel(new GridLayout(0,1));
        teamName = new JTextField();

        //FORM
        JLabel labelLastname = new JLabel("Nom : ", SwingConstants.CENTER);
        form.add(labelLastname);
        labelLastname.setLabelFor(teamName);
        form.add(teamName);

        //CHECKBOX
        JPanel checkBoxesPanel = new JPanel(new GridLayout(0, 3));
        try {
            for(Person person : Config.getAllPersonUseCase().execute()){
                JCheckBoxWithPerson checkBox = new JCheckBoxWithPerson(person.getFirstName() + " " + person.getLastName(), person);
                checkBox.addItemListener(itemListener);
                checkBoxesPanel.add(checkBox);
            }
        } catch (TechnicalException e) {
            JOptionPane.showMessageDialog(null, "Erreur technique : Veuillez contacter le service technique", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JLabel labelCheckBox = new JLabel("Sélectionner des personnes pour votre équipe : ", SwingConstants.CENTER);
        form.add(labelCheckBox);
        labelLastname.setLabelFor(checkBoxesPanel);
        form.add(checkBoxesPanel);

        return form;
    }
}
