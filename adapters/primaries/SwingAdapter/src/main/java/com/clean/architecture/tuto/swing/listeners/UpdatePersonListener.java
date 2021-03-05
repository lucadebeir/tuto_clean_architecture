package com.clean.architecture.tuto.swing.listeners;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.swing.config.Config;
import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.panels.person.CreatePersonPanel;
import com.clean.architecture.tuto.swing.gui.panels.person.MainPersonsPanel;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

@AllArgsConstructor
public class UpdatePersonListener implements ActionListener {

    private MainWindow mainWindow;

    private CreatePersonPanel createPersonPanel;

    @Override
    public void actionPerformed(ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(mainWindow, createPersonPanel.getForm(),
                "Création d'une personne", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Config.getCreatePersonUseCase().execute(Person.builder()
                        .firstName(Objects.nonNull(createPersonPanel.getFirstname()) ? createPersonPanel.getFirstname().getText() : "")
                        .lastName(Objects.nonNull(createPersonPanel.getLastname()) ? createPersonPanel.getLastname().getText() : "")
                        .age(Objects.nonNull(createPersonPanel.getAge()) && Objects.nonNull(createPersonPanel.getAge().getValue()) ? (Integer) createPersonPanel.getAge().getValue() : 0)
                        .build());

                mainWindow.updatePanel(new MainPersonsPanel(mainWindow));
            } catch (BusinessException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage().split(","), "Erreur",  JOptionPane.ERROR_MESSAGE);
            } catch (TechnicalException ex) {
                JOptionPane.showMessageDialog(null, "Erreur technique : Veuillez contacter le service technique", "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

}
