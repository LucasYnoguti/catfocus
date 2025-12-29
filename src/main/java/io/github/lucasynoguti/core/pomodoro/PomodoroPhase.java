package io.github.lucasynoguti.core.pomodoro;

public enum PomodoroPhase {
    FOCUS("FOCUS"),
    SHORT_BREAK("<html>SHORT<br>BREAK</html>"),
    LONG_BREAK("<html>LONG<br>BREAK</html>");
    private final String displayName;

    PomodoroPhase(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
