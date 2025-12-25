package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;
import io.github.lucasynoguti.core.pomodoro.PomodoroSettings;
import io.github.lucasynoguti.core.pomodoro.PomodoroState;
import io.github.lucasynoguti.core.pomodoro.SettingsDAO;

import javax.swing.Timer;

public class PomodoroController {

    private PomodoroState state;
    private PomodoroSettings settings;
    private final SettingsDAO settingsDAO;
    private final Timer swingTimer;
    private Runnable onUpdate; // Callback to notify view

    public PomodoroController(PomodoroSettings settings, SettingsDAO settingsDAO) {
        this.settingsDAO = settingsDAO;
        this.settings = settings;
        this.state = new PomodoroState(settings.focusDuration(), false, PomodoroPhase.FOCUS, 0, settings);
        this.swingTimer = new Timer(1000, e -> tick());
    }

    public void setOnUpdateCallback(Runnable callback) {
        this.onUpdate = callback;
    }

    public void updateSettings(PomodoroSettings newSettings) {
        this.settings = newSettings;
        this.settingsDAO.saveSettings(newSettings);
        if (!state.isRunning()) {
            reset();
        } else {
            this.state = state.withSettings(newSettings);
            notifyUpdate();
        }
    }

    private void tick() {
        PomodoroPhase previousPhase = state.getPhase();
        state = state.tick();
        
        // Toca o som se a fase mudar (ex: Foco -> Pausa)
        if (previousPhase != state.getPhase()) {
            SoundPlayer.playSound("/sounds/ding.wav");
        }
        
        notifyUpdate();
    }

    public void playPause() {
        if (state.isRunning()) {
            swingTimer.stop();
            state = state.pause();
        } else {
            swingTimer.start();
            state = state.start();
        }
        notifyUpdate();
    }

    public void reset() {
        swingTimer.stop();
        state = state.reset(settings);
        notifyUpdate();
    }

    private void notifyUpdate() {
        if (onUpdate != null) {
            onUpdate.run();
        }
    }

    public PomodoroState getState() {
        return state;
    }

    public PomodoroSettings getSettings() {
        return settings;
    }
}