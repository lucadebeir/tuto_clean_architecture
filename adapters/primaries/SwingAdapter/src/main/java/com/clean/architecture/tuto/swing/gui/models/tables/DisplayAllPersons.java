package com.clean.architecture.tuto.swing.gui.models.tables;

import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.swing.config.Config;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

@Getter
@Setter
public class DisplayAllPersons extends AbstractTableModel {

    private final String[] columnNames = {"Identifiant", "Prénom", "Nom", "Age"};
    private List<Person> persons;

    public DisplayAllPersons() {
        try {
            this.persons = Config.getAllPersonUseCase().execute();
        } catch (TechnicalException e) {
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return persons.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
                return String.class;

            case 3:
                return Integer.class;

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
                return persons.get(rowIndex).getId();

            case 1:
                // Prenom
                return persons.get(rowIndex).getFirstName();

            case 2:
                // Nom
                return persons.get(rowIndex).getLastName();

            case 3:
                // Age
                return persons.get(rowIndex).getAge();

            default:
                throw new IllegalArgumentException();
        }
    }
}
