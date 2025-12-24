package io.github.lucasynoguti.core.pomodoro;

import java.lang.classfile.instruction.ReturnInstruction;

public class PomodoroState {
    private final int remainingSeconds;
    private final boolean running;
    private final PomodoroPhase phase;
    private final int focusDuration;      //seconds
    private final int shortBreakDuration;
    private final int longBreakDuration;
    private final int completedFocusSessions;
    private final int numberOfSessions;

    public PomodoroState(int remainingSeconds, boolean running, PomodoroPhase phase,
                         int focusDuration, int shortBreakDuration, int longBreakDuration, int completedFocusSessions, int numberOfSessions) {

        this.remainingSeconds = remainingSeconds;
        this.running = running;
        this.phase = phase;
        this.focusDuration = focusDuration;
        this.shortBreakDuration = shortBreakDuration;
        this.longBreakDuration = longBreakDuration;
        this.completedFocusSessions = completedFocusSessions;
        this.numberOfSessions = numberOfSessions;
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
            return new PomodoroState(remainingSeconds - 1, true, phase,
                    focusDuration, shortBreakDuration, longBreakDuration, completedFocusSessions, numberOfSessions);
        } else {
            if (phase == PomodoroPhase.FOCUS) {
                int newCount = completedFocusSessions + 1;
                if (newCount > numberOfSessions) {
                    return new PomodoroState(longBreakDuration, false, PomodoroPhase.LONG_BREAK,
                            focusDuration, shortBreakDuration, longBreakDuration, 0, numberOfSessions);
                } else {
                    return new PomodoroState(shortBreakDuration, false, PomodoroPhase.SHORT_BREAK,
                            focusDuration, shortBreakDuration, longBreakDuration, newCount, numberOfSessions);
                }
            } else {
                return new PomodoroState(focusDuration, false, PomodoroPhase.FOCUS,
                        focusDuration, shortBreakDuration, longBreakDuration, completedFocusSessions, numberOfSessions);
            }
        }

    }

    public PomodoroState start() {
        return new PomodoroState(remainingSeconds, true, phase, focusDuration,
                shortBreakDuration, longBreakDuration, completedFocusSessions, numberOfSessions);
    }

    public PomodoroState pause() {
        return new PomodoroState(remainingSeconds, false, phase, focusDuration,
                shortBreakDuration, longBreakDuration, completedFocusSessions, numberOfSessions);
    }

    public PomodoroState reset(int focusDuration, int shortBreakDuration, int longBreakDuration) {
        return new PomodoroState(focusDuration, false, phase, focusDuration,
                shortBreakDuration, longBreakDuration, completedFocusSessions, numberOfSessions);
    }

    private int getPhaseDuration(PomodoroPhase phase) {
        switch (phase) {
            case FOCUS:
                return focusDuration;
            case SHORT_BREAK:
                return shortBreakDuration;
            case LONG_BREAK:
                return longBreakDuration;
            default:
                return focusDuration;
        }
    }
}
