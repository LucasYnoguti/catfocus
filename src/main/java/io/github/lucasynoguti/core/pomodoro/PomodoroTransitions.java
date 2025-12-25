package io.github.lucasynoguti.core.pomodoro;

public class PomodoroTransitions {
    private final PomodoroState state;

    public PomodoroTransitions(PomodoroState state) {
        this.state = state;
    }

    public PomodoroTransitions(int remainingSeconds, boolean running, PomodoroPhase phase, int completedFocusSessions, PomodoroSettings settings) {
        this.state = new PomodoroState(remainingSeconds, running, phase, settings, completedFocusSessions);
    }

    public PomodoroTransitions tick() {
        if (!state.running()) return this;

        if (state.remainingSeconds() > 0) {
            return withRemainingSeconds(state.remainingSeconds() - 1);
        } else {
            if (state.phase() == PomodoroPhase.FOCUS) {
                int newCount = state.completedFocusSessions() + 1;
                if (newCount >= state.settings().numberOfSessions()) {
                    return new PomodoroTransitions(
                            state.settings().longBreakDuration(),
                            false,
                            PomodoroPhase.LONG_BREAK,
                            0,
                            state.settings()
                    );
                } else {
                    return new PomodoroTransitions(
                            state.settings().shortBreakDuration(),
                            false,
                            PomodoroPhase.SHORT_BREAK,
                            newCount,
                            state.settings()
                    );
                }
            } else {
                return new PomodoroTransitions(
                        state.settings().focusDuration(),
                        false,
                        PomodoroPhase.FOCUS,
                        state.completedFocusSessions(),
                        state.settings()
                );
            }
        }
    }

    public PomodoroTransitions start() {
        return withRunning(true);
    }

    public PomodoroTransitions pause() {
        return withRunning(false);
    }

    public PomodoroTransitions reset(PomodoroSettings settings) {
        return new PomodoroTransitions(
                getPhaseDuration(state.phase(), settings),
                false,
                state.phase(),
                state.completedFocusSessions(),
                settings
        );
    }

    private int getPhaseDuration(PomodoroPhase phase, PomodoroSettings settings) {
        return switch (phase) {
            case FOCUS -> settings.focusDuration();
            case SHORT_BREAK -> settings.shortBreakDuration();
            case LONG_BREAK -> settings.longBreakDuration();
        };
    }

    // getters
    public int getRemainingSeconds() { return state.remainingSeconds(); }
    public PomodoroPhase getPhase() { return state.phase(); }
    public boolean isRunning() { return state.running(); }
    public int getCompletedFocusSessions() { return state.completedFocusSessions(); }
    public PomodoroSettings getSettings() { return state.settings(); }
    public PomodoroState getState() { return state; }


    //withers
    public PomodoroTransitions withRemainingSeconds(int seconds) {
        return new PomodoroTransitions(seconds, state.running(), state.phase(), state.completedFocusSessions(), state.settings());
    }

    public PomodoroTransitions withRunning(boolean running) {
        return new PomodoroTransitions(state.remainingSeconds(), running, state.phase(), state.completedFocusSessions(), state.settings());
    }

    public PomodoroTransitions withSettings(PomodoroSettings settings) {
        return new PomodoroTransitions(state.remainingSeconds(), state.running(), state.phase(), state.completedFocusSessions(), settings);
    }
}
