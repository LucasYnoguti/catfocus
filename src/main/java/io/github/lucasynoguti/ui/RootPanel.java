package io.github.lucasynoguti.ui;

import javax.swing.*;
import java.awt.*;

public class RootPanel extends JPanel {
    private final JPanel contentPanel;
    private final CardLayout cardLayout;

    public static final String POMODORO = "POMODORO";
    public static final String STATS = "STATS";
    public static final String CATS = "CATS";

    public RootPanel() {
        setLayout(new BorderLayout());
        setOpaque(true);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setOpaque(false);

        add(contentPanel, BorderLayout.CENTER);
    }

    public void addScreen(JPanel panel, String name) {
        JPanel wraper = new JPanel(new GridBagLayout());
        wraper.setOpaque(false);
        wraper.add(panel);
        contentPanel.add(wraper, name);
    }

    public void showScreen(String name) {
        cardLayout.show(contentPanel, name);
    }

    public void updateBackground(Color color) {
        setBackground(color);
        repaint();
    }
}
