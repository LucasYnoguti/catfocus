package io.github.lucasynoguti.ui;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {
    private JComboBox<Integer> focusDropdown;
    private JComboBox<Integer> shortBreakDropdown;
    private JComboBox<Integer> longBreakDropdown;
    private boolean confirmed = false;

    public SettingsDialog(Frame owner, int currentFocus, int currentShortBreak, int currentLongBreak) {
        super(owner, "Settings", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;


        Integer[] focusOptions = {25, 15, 20, 30, 40, 50, 60};
        Integer[] shortBreakOptions = {5, 7, 10, 15};
        Integer[] longBreakOptions = {15, 20, 30, 45, 60};

        focusDropdown = new JComboBox<>(focusOptions);
        focusDropdown.setSelectedItem(currentFocus);
        shortBreakDropdown = new JComboBox<>(shortBreakOptions);
        shortBreakDropdown.setSelectedItem(currentShortBreak);
        longBreakDropdown = new JComboBox<>(longBreakOptions);
        longBreakDropdown.setSelectedItem(currentLongBreak);

        //LINE 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Focus Duration (min):"),gbc);
        gbc.gridx = 1;
        add(focusDropdown,gbc);


        //LINE 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Short Break Duration (min):"),gbc);
        gbc.gridx = 1;
        add(shortBreakDropdown,gbc);

        //LINE 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Long Break Duration (min):"),gbc);
        gbc.gridx = 1;
        add(longBreakDropdown,gbc);

        //LINE 3
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        add(saveBtn,gbc);

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(owner);
    }

    public boolean isConfirmed() { return confirmed; }
    public int getFocusMinutes() { return (Integer) focusDropdown.getSelectedItem(); }
    public int getShortBreakMinutes() { return (Integer) shortBreakDropdown.getSelectedItem(); }
    public int getLongBreakMinutes() { return (Integer) longBreakDropdown.getSelectedItem(); }
}
