package io.github.lucasynoguti.ui;

import javax.swing.*;
import java.awt.*;

public class AppButton extends JButton {
    public AppButton(String text) {
        super(text);
        setFocusPainted(false);
        setBackground(AppTheme.FOCUS_COLOR);
        setForeground(Color.WHITE);
    }
}
