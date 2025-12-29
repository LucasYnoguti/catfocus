package io.github.lucasynoguti.ui;

import javax.swing.*;
import java.awt.*;

public class RootPanel extends JPanel {

    private final CardLayout cardLayout;

    public static final String POMODORO = "POMODORO";
    public static final String STATS = "STATS";
    public static final String CATS = "CATS";

    public RootPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setOpaque(true);
    }

    public void addScreen(JPanel panel, String name) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);
        container.add(panel);
        add(container, name);
    }

    public void showScreen(String name) {
        cardLayout.show(this, name);
    }

    public void updateBackground(Color color) {
        setBackground(color);
        repaint();
    }
}
