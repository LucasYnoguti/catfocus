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

    public PomodoroState(int remainingSeconds, boolean running, PomodoroPhase phase,
                         int focusDuration, int shortBreakDuration, int longBreakDuration, int completedFocusSessions) {

        this.remainingSeconds = remainingSeconds;
        this.running = running;
        this.phase = phase;
        this.focusDuration = focusDuration;
        this.shortBreakDuration = shortBreakDuration;
        this.longBreakDuration = longBreakDuration;
        this.completedFocusSessions = completedFocusSessions;
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
                    focusDuration, shortBreakDuration, longBreakDuration, completedFocusSessions);
        } else {
            if (phase == PomodoroPhase.FOCUS) {
                int newCount = completedFocusSessions + 1;
                if (newCount > 2) { // 2 ciclos de foco atingidos
                    return new PomodoroState(longBreakDuration, false, PomodoroPhase.LONG_BREAK,
                            focusDuration, shortBreakDuration, longBreakDuration, 0);
                } else {
                    return new PomodoroState(shortBreakDuration, false, PomodoroPhase.SHORT_BREAK,
                            focusDuration, shortBreakDuration, longBreakDuration, newCount);
                }
            } else {
                return new PomodoroState(focusDuration, false, PomodoroPhase.FOCUS,
                        focusDuration, shortBreakDuration, longBreakDuration, completedFocusSessions);
            }
        }

    }

    public PomodoroState start() {
        return new PomodoroState(remainingSeconds, true, phase, focusDuration,
                shortBreakDuration, longBreakDuration, completedFocusSessions);
    }

    public PomodoroState pause() {
        return new PomodoroState(remainingSeconds, false, phase, focusDuration,
                shortBreakDuration, longBreakDuration, completedFocusSessions);
    }

    public PomodoroState reset(int focusDuration, int shortBreakDuration, int longBreakDuration) {
        return new PomodoroState(focusDuration, false, phase, focusDuration,
                shortBreakDuration, longBreakDuration, completedFocusSessions);
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
