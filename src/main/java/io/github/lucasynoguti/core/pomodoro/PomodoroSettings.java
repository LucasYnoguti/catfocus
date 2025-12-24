package io.github.lucasynoguti.core.pomodoro;

public record PomodoroSettings(
        int focusDuration,
        int shortBreakDuration,
        int longBreakDuration,
        int numberOfSessions
) {
}
