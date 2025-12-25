package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.database.dao.SettingsDAO;
import io.github.lucasynoguti.core.pomodoro.*;

import javax.swing.Timer;

public class PomodoroController {

    private PomodoroTransitions transitions;
    private final SettingsDAO settingsDAO;
    private final Timer swingTimer;
    private Runnable onUpdate; // Callback to notify view

    public PomodoroController(PomodoroSettings settings, SettingsDAO settingsDAO) {
        this.settingsDAO = settingsDAO;
        PomodoroState initialState = new PomodoroState(
                settings.focusDuration(),
                false,
                PomodoroPhase.FOCUS,
                settings,
                0
        );
        this.transitions = new PomodoroTransitions(initialState);
        this.swingTimer = new Timer(1000, e -> tick());
    }

    public void setOnUpdateCallback(Runnable callback) {
        this.onUpdate = callback;
    }

    public void updateSettings(PomodoroSettings newSettings) {
        this.settingsDAO.saveSettings(newSettings);
        if (!transitions.isRunning()) {
            this.transitions = transitions.reset(newSettings);
        } else {
            this.transitions = transitions.withSettings(newSettings);
        }
        notifyUpdate();
    }

    private void tick() {
        PomodoroPhase previousPhase = transitions.getPhase();
        this.transitions = transitions.tick();
        if (previousPhase != transitions.getPhase()) {
            SoundPlayer.playSound("/sounds/ding.wav");
        }
        
        notifyUpdate();
    }

    public void playPause() {
        if (transitions.isRunning()) {
            swingTimer.stop();
            this.transitions = transitions.pause();
        } else {
            swingTimer.start();
            this.transitions = transitions.start();
        }
        notifyUpdate();
    }

    public void reset() {
        swingTimer.stop();
        this.transitions = transitions.reset(transitions.getSettings());
        notifyUpdate();
    }

    private void notifyUpdate() {
        if (onUpdate != null) {
            onUpdate.run();
        }
    }
    public PomodoroState getState() {
        return transitions.getState();
    }
    public PomodoroSettings getSettings() {
        return transitions.getSettings();
    }
}