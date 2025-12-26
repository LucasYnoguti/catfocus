package io.github.lucasynoguti.core.pomodoro;

public record PomodoroSettings(
        int focusDuration,
        int shortBreakDuration,
        int longBreakDuration,
        int numberOfSessions
) {
    public static PomodoroSettings defaultSettings() {
        return new PomodoroSettings(25 * 60, 5 * 60, 15 * 60, 4);
    }
}