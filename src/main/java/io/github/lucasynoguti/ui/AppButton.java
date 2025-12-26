package io.github.lucasynoguti.ui;

import javax.swing.*;
import java.awt.*;

public class AppButton extends JButton {
    public AppButton(String text) {
        super(text);
        setFocusPainted(false);
        setBackground(Color.WHITE);
        setForeground(AppTheme.PRIMARY_COLOR);
    }
}
