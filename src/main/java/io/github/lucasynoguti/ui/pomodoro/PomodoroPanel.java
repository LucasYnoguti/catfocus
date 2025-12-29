package io.github.lucasynoguti.ui.pomodoro;
import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;
import io.github.lucasynoguti.core.pomodoro.PomodoroState;
import io.github.lucasynoguti.ui.components.AppButton;
import io.github.lucasynoguti.ui.theme.AppTheme;

import javax.swing.*;
import java.awt.*;

public class PomodoroPanel extends JPanel {

    private JLabel timeLabel;
    private JLabel phaseLabel;
    private JButton playPauseBtn;
    private JButton resetBtn;
    private JButton settingsBtn;

    public PomodoroPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        phaseLabel = new JLabel("FOCUS", SwingConstants.CENTER);
        timeLabel = new JLabel("25:00", SwingConstants.CENTER);
        phaseLabel.setForeground(AppTheme.WHITE);
        timeLabel.setForeground(AppTheme.WHITE);

        JPanel timerPanel = new JPanel(new BorderLayout());
        timerPanel.setOpaque(false);
        timerPanel.add(phaseLabel, BorderLayout.NORTH);
        timerPanel.add(timeLabel, BorderLayout.CENTER);

        playPauseBtn = new AppButton("▶");
        resetBtn = new AppButton("Reset");
        settingsBtn = new AppButton("⚙");

        JPanel buttons = new JPanel();
        buttons.add(playPauseBtn);
        buttons.add(resetBtn);
        buttons.add(settingsBtn);
        buttons.setOpaque(false);

        add(timerPanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    // getters para o controller
    public JButton getPlayPauseBtn() { return playPauseBtn; }
    public JButton getResetBtn() { return resetBtn; }
    public JButton getSettingsBtn() { return settingsBtn; }

    public void updateTime(String time) {
        timeLabel.setText(time);
    }

    public void updatePhase(PomodoroPhase phase) {
        phaseLabel.setText(phase.getDisplayName());
    }

    public void updateFontSizes(int containerHeight, int containerWidth) {
        int min = Math.min(containerHeight, containerWidth);

        Font labelFont = AppTheme.MAIN_FONT
                .deriveFont(Font.PLAIN, min/ 5);

        timeLabel.setFont(labelFont);
        phaseLabel.setFont(labelFont);

        Font buttonFont = new Font(
                "SansSerif",
                Font.BOLD,
                (min) / 12
        );

        playPauseBtn.setFont(buttonFont);
        resetBtn.setFont(buttonFont);
        settingsBtn.setFont(buttonFont);
    }

    public void updateButtons(boolean running, PomodoroPhase phase) {
        playPauseBtn.setText(running ? "⏸" : "▶");
        resetBtn.setForeground(AppTheme.getColorForPhase(phase));
        playPauseBtn.setForeground(AppTheme.getColorForPhase(phase));
        settingsBtn.setForeground(AppTheme.getColorForPhase(phase));
    }

    public void onResize(int containerHeight, int containerWidth) {
        updateFontSizes(containerHeight, containerWidth);
    }

    public void renderState(PomodoroState state) {
        updateTime(formatTime(state.remainingSeconds()));
        updatePhase(state.phase());
        updateButtons(state.running(), state.phase());
    }

    private String formatTime(int totalSeconds) {
        int min = totalSeconds / 60;
        int sec = totalSeconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

}
