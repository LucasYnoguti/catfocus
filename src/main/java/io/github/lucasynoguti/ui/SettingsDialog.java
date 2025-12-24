package io.github.lucasynoguti.ui;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {
    private JComboBox<Integer> focusDropdown;
    private JComboBox<Integer> shortBreakDropdown;
    private boolean confirmed = false;

    public SettingsDialog(Frame owner, int currentFocus, int currentShortBreak) {
        super(owner, "Settings", true);
        setLayout(new GridLayout(3, 2, 10, 10));

        Integer[] focusOptions = {25, 15, 20, 30, 40, 50, 60};
        Integer[] shortBreakOptions = {5, 7, 10, 15};

        focusDropdown = new JComboBox<>(focusOptions);
        focusDropdown.setSelectedItem(currentFocus);

        shortBreakDropdown = new JComboBox<>(shortBreakOptions);
        shortBreakDropdown.setSelectedItem(currentShortBreak);

        add(new JLabel("Focus Duration (min):"));
        add(focusDropdown);
        add(new JLabel("Short Break Duration (min):"));
        add(shortBreakDropdown);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        add(saveBtn);

        pack();
        setLocationRelativeTo(owner);
    }

    public boolean isConfirmed() { return confirmed; }
    public int getFocusMinutes() { return (Integer) focusDropdown.getSelectedItem(); }
    public int getShortBreakMinutes() { return (Integer) shortBreakDropdown.getSelectedItem(); }
}
