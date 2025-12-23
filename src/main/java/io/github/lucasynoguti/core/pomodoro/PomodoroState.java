package io.github.lucasynoguti.core.pomodoro;

public class PomodoroState {
    private final int remainingSeconds;
    private final boolean running;
    private final PomodoroPhase phase;

    public PomodoroState(int remainingSeconds, boolean running, PomodoroPhase phase) {

        this.remainingSeconds = remainingSeconds;
        this.running = running;
        this.phase = phase;
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
        if(remainingSeconds>0){
            return new PomodoroState(remainingSeconds - 1, true, phase);
        }
        else{
            PomodoroPhase nextPhase = getNextPhase();
            int nextSeconds = getPhaseDuration(nextPhase);
            return new PomodoroState(nextSeconds, true, nextPhase);
        }

    }

    public PomodoroState start() {
        return new PomodoroState(remainingSeconds, true, phase);
    }

    public PomodoroState pause() {
        return new PomodoroState(remainingSeconds, false, phase);
    }

    public PomodoroState reset(int totalSeconds) {
        return new PomodoroState(totalSeconds, false, phase);
    }

    private PomodoroPhase getNextPhase() {
        return phase == PomodoroPhase.FOCUS ? PomodoroPhase.SHORT_BREAK : PomodoroPhase.FOCUS;
    }

    private int getPhaseDuration(PomodoroPhase phase) {
        switch (phase) {
            case FOCUS: return 5;
            case SHORT_BREAK: return 3;
            default: return 25 * 60;
        }
    }
}
