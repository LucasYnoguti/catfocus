package io.github.lucasynoguti.core.pomodoro;

public enum PomodoroPhase {
    FOCUS("FOCUS"),
    SHORT_BREAK("SHORT BREAK"),
    LONG_BREAK("LONG BREAK");
    private final String displayName;

    PomodoroPhase(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
