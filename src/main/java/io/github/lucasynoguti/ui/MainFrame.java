package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;
import io.github.lucasynoguti.core.pomodoro.PomodoroSettings;
import io.github.lucasynoguti.core.pomodoro.PomodoroState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class MainFrame extends JFrame {
    private PomodoroPanel pomodoroPanel;
    private PomodoroState state;
    private Timer swingTimer;
    private PomodoroSettings settings;


    public MainFrame() {
        setTitle("CatFocus");
        setSize(500, 300);
        setMinimumSize(new Dimension(300, 200));
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //model
        settings = new PomodoroSettings(25 * 60, 5 * 60, 15 * 60, 2);
        state = new PomodoroState(settings.focusDuration(), false, PomodoroPhase.FOCUS, 0, settings);

        //view
        pomodoroPanel = new PomodoroPanel();
        add(pomodoroPanel);

        swingTimer = new Timer(1000, e -> {
            state = state.tick();
            updateView();
        });

        //actions
        pomodoroPanel.getPlayPauseBtn().addActionListener(e -> playPause());
        pomodoroPanel.getResetBtn().addActionListener(e -> reset());
        pomodoroPanel.getSettingsBtn().addActionListener(e -> openSettings());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                pomodoroPanel.updateFontSizes(getHeight());
            }
        });
    }

    // Controller logic
    private void playPause() {
        if (state.isRunning()) {
            state = state.pause();
            swingTimer.stop();
        } else {
            state = state.start();
            swingTimer.start();
        }
        updateView();
    }

    private void reset() {
        state = state.reset(settings);
        swingTimer.stop();
        updateView();
    }

    private void openSettings() {
        SettingsDialog dialog = new SettingsDialog(this, settings);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            settings = new PomodoroSettings(
                    dialog.getFocusMinutes() * 60,
                    dialog.getShortBreakMinutes() * 60,
                    dialog.getLongBreakMinutes() * 60,
                    dialog.getNumberOfSessions()
            );
            if (!state.isRunning()) {
                state = state.reset(settings);
                updateView();
            }
        }
    }

    //View update
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


    private void updateView() {
        pomodoroPanel.updateTime(formatTime(state.getRemainingSeconds()));
        pomodoroPanel.updatePhase(formatPhase(state.getPhase()));
        pomodoroPanel.updatePlayPause(state.isRunning());
    }
}
