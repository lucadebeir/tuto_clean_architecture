package com.clean.architecture.tuto.swing.gui.models.tables;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.core.exceptions.TechnicalException;
import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.swing.config.Config;
import com.clean.architecture.tuto.swing.gui.frames.MainWindow;
import com.clean.architecture.tuto.swing.gui.panels.person.MainPersonsPanel;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.Objects;

public class TableCell implements TableCellEditor, TableCellRenderer {

    public JButton btn;
    private int row;

    public TableCell(JTable table, MainWindow mainWindow) {
        btn = new JButton("Supprimer");
        btn.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            try {
                Config.deletePersonUseCase().execute(model.getColumnName(Integer.parseInt("id")));

                mainWindow.updatePanel(new MainPersonsPanel(mainWindow));
            } catch (BusinessException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage().split(","), "Erreur",  JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur technique : Veuillez contacter le service technique", "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        return btn;
    }

    @Override
    public Object getCellEditorValue() {
        return true;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        return true;
    }

    @Override
    public void cancelCellEditing() {

    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {

    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return btn;
    }
}
