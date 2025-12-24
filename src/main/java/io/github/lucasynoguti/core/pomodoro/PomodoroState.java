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

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public PomodoroPhase getPhase() {
        return phase;
    }

    public boolean isRunning() {
        return running;
    }

    public PomodoroState tick() {
        if (!running) return this;
        if (remainingSeconds > 0) {
            return new PomodoroState(remainingSeconds - 1, true, phase, completedFocusSessions, settings);
        } else {
            if (phase == PomodoroPhase.FOCUS) {
                int newCount = completedFocusSessions + 1;
                if (newCount > settings.numberOfSessions()) {
                    return new PomodoroState(settings.longBreakDuration(), false, PomodoroPhase.LONG_BREAK, 0, settings);
                } else {
                    return new PomodoroState(settings.shortBreakDuration(), false, PomodoroPhase.SHORT_BREAK, newCount, settings);
                }
            } else {
                return new PomodoroState(settings.focusDuration(), false, PomodoroPhase.FOCUS, completedFocusSessions, settings);
            }
        }

    }

    public PomodoroState start() {
        return new PomodoroState(remainingSeconds, true, phase, completedFocusSessions, settings);
    }

    public PomodoroState pause() {
        return new PomodoroState(remainingSeconds, false, phase, completedFocusSessions, settings);
    }

    public PomodoroState reset(PomodoroSettings settings) {
        return new PomodoroState(getPhaseDuration(phase), false, phase, completedFocusSessions, settings);
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
}
