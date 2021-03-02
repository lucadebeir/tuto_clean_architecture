package com.clean.architecture.tuto.swing.gui.models.tables;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.models.Team;
import com.clean.architecture.tuto.swing.config.Config;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

@Getter
@Setter
public class DisplayAllTeams extends AbstractTableModel {

    private final String[] columnNames = {"Identifiant", "Nom"};
    public List<Team> teams;

    public DisplayAllTeams() {
        try {
            this.teams = Config.getAllTeamUseCase().execute();
        } catch (TechnicalException e) {
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return teams.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
                return String.class;

            case 2:
                return List.class;

            default:
                return Object.class;
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {

            case 0:
                // Identifiant
                return teams.get(rowIndex).getId();

            case 1:
                // Nom
                return teams.get(rowIndex).getName();

            case 2:
                // List
                return teams.get(rowIndex).getList();

            default:
                throw new IllegalArgumentException();
        }
    }

}
