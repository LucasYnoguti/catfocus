package io.github.lucasynoguti.ui;

import javax.swing.*;
import java.awt.*;

public class AppButton extends JButton {
    public AppButton(String text) {
        super(text);
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setFocusPainted(false);
    }
}
