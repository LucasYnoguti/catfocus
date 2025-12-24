package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;
import io.github.lucasynoguti.core.pomodoro.PomodoroSettings;
import io.github.lucasynoguti.core.pomodoro.PomodoroState;

import javax.swing.*;

public class PomodoroController {

    private PomodoroState state;
    private PomodoroSettings settings;
    private Timer swingTimer;
    private Runnable onTick; // callback para a UI

    public PomodoroController(PomodoroSettings settings, Runnable onTick) {
        this.settings = settings;
        this.onTick = onTick;
        state = new PomodoroState(settings.focusDuration(), false, PomodoroPhase.FOCUS, 0, settings);
        swingTimer = new Timer(1000, e -> tick());
    }

    private void tick() {
        state = state.tick();
        onTick.run();
    }

    public void playPause() {
        if (state.isRunning()) {
            swingTimer.stop();
            state = state.pause();
        }
        else {
            swingTimer.start();
            state = state.start();
        }
        if(onTick != null) onTick.run();
    }

    public void reset() {
        swingTimer.stop();
        state = state.reset(settings);
        onTick.run();
    }

    public PomodoroState getState() {
        return state;
    }

    public PomodoroSettings getSettings() {
        return settings;
    }
    public void updateSettings(PomodoroSettings newSettings) {
        this.settings = newSettings;
        if (!state.isRunning()) reset();
        else this.state = state.withSettings(newSettings);
    }
}