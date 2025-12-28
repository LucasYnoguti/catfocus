package io.github.lucasynoguti.core.pomodoro;

import io.github.lucasynoguti.core.database.dao.SessionDAO;
import io.github.lucasynoguti.core.database.dao.SettingsDAO;
import io.github.lucasynoguti.util.SoundPlayer;

import javax.swing.Timer;

public class PomodoroController {

    private PomodoroTransitions transitions;
    private final SettingsDAO settingsDAO;
    private final SessionDAO sessionDAO;
    private final Timer swingTimer;
    private Runnable onUpdate; // Callback to notify view
    private long currentSessionId = -1;
    private long sessionStartTime = -1;

    public PomodoroController(PomodoroSettings settings, SettingsDAO settingsDAO, SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
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
            finishCurrentSession(true);
            SoundPlayer.playSound("/sounds/ding.wav");
        }

        notifyUpdate();
    }

    public void playPause() {
        if (transitions.isRunning()) {
            swingTimer.stop();
            this.transitions = transitions.pause();
        } else {
            if (currentSessionId == -1) {
                PomodoroState state = transitions.getState();
                sessionStartTime = System.currentTimeMillis();
                currentSessionId = sessionDAO.beginSession(
                        state.phase(),
                        transitions.getSettings().focusDuration(),
                        sessionStartTime
                );
            }
            swingTimer.start();
            this.transitions = transitions.start();
        }
        notifyUpdate();
    }

    public void reset() {
        if (currentSessionId != -1) {
            finishCurrentSession(false);
        }
        swingTimer.stop();
        this.transitions = transitions.reset(transitions.getSettings());
        System.out.println(sessionDAO.getTotalFocusTime());
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

    private void finishCurrentSession(boolean completed) {
        long sessionFinishTime = System.currentTimeMillis();
        int actualDuration = (int) ((sessionFinishTime - sessionStartTime) / 1000);

        sessionDAO.finishSession(
                currentSessionId,
                completed,
                actualDuration,
                sessionFinishTime
        );
        currentSessionId = -1;
        sessionStartTime = -1;
    }

    public void onAppClose() {
        if(currentSessionId != -1)
        {
            finishCurrentSession(false);
        }
    }
}