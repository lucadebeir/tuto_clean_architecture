package com.clean.architecture.tuto.swing.listeners;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.swing.config.Config;
import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.panels.team.CreateTeamPanel;
import com.clean.architecture.tuto.swing.gui.panels.team.MainTeamsPanel;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

@AllArgsConstructor
/**
 * Listener pour la création d'une personne
 */
public class CreateTeamListener implements ActionListener {

    private MainWindow mainWindow;

    private CreateTeamPanel createTeamPanel;

    @Override
    public void actionPerformed(ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(mainWindow, createTeamPanel.getForm(),
                "Création d'une équipe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Config.getCreateTeamUseCase().execute(Team.builder()
                        .name(Objects.nonNull(createTeamPanel.getTeamName()) ? createTeamPanel.getTeamName().getText() : "")
                        .list(createTeamPanel.getSelectPersons())
                        .build());

                mainWindow.updatePanel(new MainTeamsPanel(mainWindow));
            } catch (BusinessException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage().split(","), "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (TechnicalException ex) {
                JOptionPane.showMessageDialog(null, "Erreur technique : Veuillez contacter le service technique", "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
