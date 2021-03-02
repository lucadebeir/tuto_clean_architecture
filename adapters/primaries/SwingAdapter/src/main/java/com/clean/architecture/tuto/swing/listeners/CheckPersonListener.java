package com.clean.architecture.tuto.swing.listeners;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.swing.gui.models.checkbox.JCheckBoxWithPerson;
import com.clean.architecture.tuto.swing.gui.panels.team.CreateTeamPanel;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

@AllArgsConstructor
public class CheckPersonListener implements ItemListener {

    CreateTeamPanel createTeamPanel;

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() instanceof JCheckBox){
            JCheckBoxWithPerson checkBox = (JCheckBoxWithPerson) e.getSource();
            if(checkBox.isSelected()){
                createTeamPanel.getSelectPersons().add(checkBox.getPerson());
            } else {
                createTeamPanel.getSelectPersons().remove(checkBox.getPerson());
            }
        }
    }
}
