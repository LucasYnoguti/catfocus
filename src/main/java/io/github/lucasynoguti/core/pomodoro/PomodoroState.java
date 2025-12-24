package io.github.lucasynoguti.core.pomodoro;

import java.lang.classfile.instruction.ReturnInstruction;

public class PomodoroState {
    private final int remainingSeconds;
    private final boolean running;
    private final PomodoroPhase phase;
    private final int focusDuration;      //seconds
    private final int shortBreakDuration;
    private final int longBreakDuration;

    public PomodoroState(int remainingSeconds, boolean running, PomodoroPhase phase, int focusDuration, int shortBreakDuration, int longBreakDuration) {

        this.remainingSeconds = remainingSeconds;
        this.running = running;
        this.phase = phase;
        this.focusDuration = focusDuration;
        this.shortBreakDuration = shortBreakDuration;
        this.longBreakDuration = longBreakDuration;
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
            return new PomodoroState(remainingSeconds - 1, true, phase, focusDuration, shortBreakDuration, longBreakDuration);
        } else {
            PomodoroPhase nextPhase = getNextPhase();
            int nextSeconds = getPhaseDuration(nextPhase);
            return new PomodoroState(nextSeconds, true, nextPhase, focusDuration, shortBreakDuration, longBreakDuration);
        }

    }

    public PomodoroState start() {
        return new PomodoroState(remainingSeconds, true, phase, focusDuration, shortBreakDuration, longBreakDuration);
    }

    public PomodoroState pause() {
        return new PomodoroState(remainingSeconds, false, phase, focusDuration, shortBreakDuration, longBreakDuration);
    }

    public PomodoroState reset(int focusDuration, int shortBreakDuration) {
        return new PomodoroState(focusDuration, false, phase, focusDuration, shortBreakDuration, longBreakDuration);
    }

    private PomodoroPhase getNextPhase() {
        switch (phase) {
            case FOCUS:
                return PomodoroPhase.SHORT_BREAK;
            case SHORT_BREAK:
                return PomodoroPhase.FOCUS;
            case LONG_BREAK:
                return PomodoroPhase.FOCUS;
            default:
                return PomodoroPhase.FOCUS;
        }
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
