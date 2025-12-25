package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.pomodoro.PomodoroSettings;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {
    private JComboBox<Integer> focusDropdown;
    private JComboBox<Integer> shortBreakDropdown;
    private JComboBox<Integer> longBreakDropdown;
    private JComboBox<Integer> numberOfSessionsDropdown;
    private boolean confirmed = false;

    public SettingsDialog(Frame owner, PomodoroSettings settings) {
        super(owner, "Settings", true);
        getContentPane().setBackground(AppTheme.BG_COLOR);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        Integer[] numberOfSessionsOptions = {2, 1, 3, 4};
        Integer[] focusOptions = {25, 15, 20, 30, 40, 50, 60, 0};
        Integer[] shortBreakOptions = {5, 7, 10, 15, 0};
        Integer[] longBreakOptions = {15, 20, 30, 45, 60,0};

        focusDropdown = new JComboBox<>(focusOptions);
        focusDropdown.setSelectedItem(settings.focusDuration() / 60);

        shortBreakDropdown = new JComboBox<>(shortBreakOptions);
        shortBreakDropdown.setSelectedItem(settings.shortBreakDuration() / 60);

        longBreakDropdown = new JComboBox<>(longBreakOptions);
        longBreakDropdown.setSelectedItem(settings.longBreakDuration() / 60);

        numberOfSessionsDropdown = new JComboBox<>(numberOfSessionsOptions);
        numberOfSessionsDropdown.setSelectedItem(settings.numberOfSessions());

        //LINE 0
        addSettingRow("Focus Duration (min):", focusDropdown, 0, gbc);
        addSettingRow("Sort Break Duration (min):", shortBreakDropdown, 1, gbc);
        addSettingRow("Long Break Duration (min):", longBreakDropdown, 2, gbc);
        addSettingRow("Sessions per cycle:", numberOfSessionsDropdown, 3, gbc);

        //LINE 4
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        add(saveBtn, gbc);

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(owner);
    }

    private void addSettingRow(String labelText, JComponent component, int row, GridBagConstraints gbc) {
        gbc.gridy = row;
        gbc.gridx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif",Font.PLAIN, 12));
        add(label, gbc);
        gbc.gridx = 1;
        component.setFont(new Font("SansSerif",Font.PLAIN, 12));
        add(component, gbc);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getFocusMinutes() {
        return (Integer) focusDropdown.getSelectedItem();
    }

    public int getShortBreakMinutes() {
        return (Integer) shortBreakDropdown.getSelectedItem();
    }

    public int getLongBreakMinutes() {
        return (Integer) longBreakDropdown.getSelectedItem();
    }

    public int getNumberOfSessions() {
        return (Integer) numberOfSessionsDropdown.getSelectedItem();
    }
}
