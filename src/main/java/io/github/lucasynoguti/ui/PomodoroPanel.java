package io.github.lucasynoguti.ui;
import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;

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

    public void updateFontSizes(int containerHeight) {
        Font labelFont = AppTheme.MAIN_FONT
                .deriveFont(Font.PLAIN, containerHeight / 5f);

        timeLabel.setFont(labelFont);
        phaseLabel.setFont(labelFont);

        Font buttonFont = new Font(
                "SansSerif",
                Font.BOLD,
                containerHeight / 12
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

}
