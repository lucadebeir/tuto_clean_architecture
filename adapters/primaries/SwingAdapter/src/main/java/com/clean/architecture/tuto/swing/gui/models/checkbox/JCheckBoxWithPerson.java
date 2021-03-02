package com.clean.architecture.tuto.swing.gui.models.checkbox;

import com.clean.architecture.tuto.core.models.Person;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class JCheckBoxWithPerson extends JCheckBox {

    private Person person;

    public JCheckBoxWithPerson(String text, Person person) {
        super(text);
        this.person = person;
    }
}
