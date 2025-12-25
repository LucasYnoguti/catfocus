package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;
import io.github.lucasynoguti.core.pomodoro.PomodoroSettings;
import io.github.lucasynoguti.core.pomodoro.PomodoroState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class MainFrame extends JFrame {
    private final PomodoroPanel pomodoroPanel;
    private final PomodoroController controller;

    public MainFrame(PomodoroController controller) {
        this.controller = controller;

        setTitle("CatFocus");
        setSize(500, 300);
        setMinimumSize(new Dimension(300, 200));
        setLayout(new GridBagLayout());
        getContentPane().setBackground(AppTheme.BG_COLOR);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        //view
        pomodoroPanel = new PomodoroPanel();
        add(pomodoroPanel);

        //actions
        pomodoroPanel.getPlayPauseBtn().addActionListener(e -> controller.playPause());
        pomodoroPanel.getResetBtn().addActionListener(e -> controller.reset());
        pomodoroPanel.getSettingsBtn().addActionListener(e -> openSettings());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                pomodoroPanel.updateFontSizes(getHeight());
            }
        });

        updateView();
    }

    private void openSettings() {
        SettingsDialog dialog = new SettingsDialog(this, controller.getSettings());
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            PomodoroSettings newSettings = new PomodoroSettings(
                    dialog.getFocusMinutes() * 60,
                    dialog.getShortBreakMinutes() * 60,
                    dialog.getLongBreakMinutes() * 60,
                    dialog.getNumberOfSessions()
            );
            controller.updateSettings(newSettings);
            updateView();
        }
    }

    public void updateView() {
        PomodoroState state = controller.getState();
        pomodoroPanel.updateTime(formatTime(state.getRemainingSeconds()));
        pomodoroPanel.updatePhase(formatPhase(state.getPhase()));
        pomodoroPanel.updatePlayPause(state.isRunning());
    }

    //helpers
    private String formatTime(int totalSeconds) {
        int min = totalSeconds / 60;
        int sec = totalSeconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

    private String formatPhase(PomodoroPhase phase) {
        return switch (phase) {
            case FOCUS -> "FOCUS";
            case SHORT_BREAK -> "SHORT BREAK";
            case LONG_BREAK -> "LONG BREAK";
        };
    }
}