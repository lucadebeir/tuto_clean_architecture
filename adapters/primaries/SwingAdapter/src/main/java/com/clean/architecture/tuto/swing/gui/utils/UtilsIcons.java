package com.clean.architecture.tuto.swing.gui.utils;

import javax.swing.*;
import java.awt.*;

public class UtilsIcons {

    public Icon resizeImage(String url) {
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(url));
        Image image = imageIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        return new ImageIcon(image);
    }
}
