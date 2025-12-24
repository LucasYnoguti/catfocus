package io.github.lucasynoguti.core.pomodoro;

import java.lang.classfile.instruction.ReturnInstruction;

public class PomodoroState {
    private final int remainingSeconds;
    private final boolean running;
    private final PomodoroPhase phase;
    private final PomodoroSettings settings;
    private final int completedFocusSessions;

    public PomodoroState(int remainingSeconds, boolean running, PomodoroPhase phase, int completedFocusSessions, PomodoroSettings settings) {

        this.remainingSeconds = remainingSeconds;
        this.running = running;
        this.phase = phase;
        this.completedFocusSessions = completedFocusSessions;
        this.settings = settings;
    }



    public PomodoroState tick() {
        if (!running) return this;
        if (remainingSeconds > 0) {
            return withRemainingSeconds(remainingSeconds - 1);
        } else {
            if (phase == PomodoroPhase.FOCUS) {
                int newCount = completedFocusSessions + 1;
                if (newCount > settings.numberOfSessions()) {
                    newCount = 0;
                    return this
                            .withRemainingSeconds(settings.longBreakDuration())
                            .withRunning(false)
                            .withPhase(PomodoroPhase.LONG_BREAK)
                            .withCompletedFocusSessions(newCount);
                } else {
                    return this
                            .withRemainingSeconds(settings.shortBreakDuration())
                            .withRunning(false)
                            .withPhase(PomodoroPhase.SHORT_BREAK)
                            .withCompletedFocusSessions(newCount);
                }
            } else {
                return this
                        .withRemainingSeconds(settings.focusDuration())
                        .withRunning(false)
                        .withPhase(PomodoroPhase.FOCUS);
            }
        }

    }

    public PomodoroState start() {
        return withRunning(true);
    }

    public PomodoroState pause() {
        return withRunning(false);
    }

    public PomodoroState reset(PomodoroSettings settings) {
        return this
                .withRemainingSeconds(getPhaseDuration(phase))
                .withRunning(false)
                .withSettings(settings);
    }

    private int getPhaseDuration(PomodoroPhase phase) {
        switch (phase) {
            case FOCUS:
                return settings.focusDuration();
            case SHORT_BREAK:
                return settings.shortBreakDuration();
            case LONG_BREAK:
                return settings.longBreakDuration();
            default:
                return settings.focusDuration();
        }
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public PomodoroPhase getPhase() {
        return phase;
    }

    public boolean isRunning() {
        return running;
    }

    // WITHERS
    public PomodoroState withRemainingSeconds(int remainingSeconds) {
        return new PomodoroState(remainingSeconds, running, phase, completedFocusSessions, settings);
    }

    public PomodoroState withRunning(boolean running) {
        return new PomodoroState(remainingSeconds, running, phase, completedFocusSessions, settings);
    }

    public PomodoroState withPhase(PomodoroPhase phase) {
        return new PomodoroState(remainingSeconds, running, phase, completedFocusSessions, settings);
    }

    public PomodoroState withCompletedFocusSessions(int completedFocusSessions) {
        return new PomodoroState(remainingSeconds, running, phase, completedFocusSessions, settings);
    }

    public PomodoroState withSettings(PomodoroSettings settings) {
        return new PomodoroState(remainingSeconds, running, phase, completedFocusSessions, settings);
    }
}
