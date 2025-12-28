package io.github.lucasynoguti.ui.components;

import io.github.lucasynoguti.ui.pomodoro.AppTheme;

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
