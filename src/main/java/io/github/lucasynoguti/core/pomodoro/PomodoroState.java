package io.github.lucasynoguti.core.pomodoro;

public record PomodoroState(
        int remainingSeconds,
        boolean running,
        PomodoroPhase phase,
        PomodoroSettings settings,
        int completedFocusSessions
) {
}
