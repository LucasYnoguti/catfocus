package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;
import io.github.lucasynoguti.core.pomodoro.PomodoroSettings;
import io.github.lucasynoguti.core.pomodoro.PomodoroState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class MainFrame extends JFrame {

    private JLabel timeLabel;
    private JLabel phaseLabel;
    private JButton playPauseBtn;
    private JButton resetBtn;
    private JButton settingsBtn;
    private PomodoroState state;
    private Timer swingTimer;
    private PomodoroSettings settings;


    public MainFrame() {
        setTitle("CatFocus");
        setSize(500, 300);
        setMinimumSize(new Dimension(400, 200));
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //initial state
        settings = new PomodoroSettings(25 * 60, 5 * 60, 15 * 60, 2);
        state = new PomodoroState(settings.focusDuration(), false, PomodoroPhase.FOCUS, 0, settings);
        phaseLabel = new JLabel(formatPhase(state.getPhase()), SwingConstants.CENTER);
        phaseLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
        phaseLabel.setForeground(AppTheme.FOCUS_COLOR);
        timeLabel = new JLabel(formatTime(state.getRemainingSeconds()), SwingConstants.CENTER);
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
        timeLabel.setForeground(AppTheme.FOCUS_COLOR);

        playPauseBtn = new AppButton("▶");
        resetBtn = new AppButton("Reset");
        settingsBtn = new AppButton("⚙");


        JPanel buttons = new JPanel();
        buttons.add(playPauseBtn);
        buttons.add(resetBtn);
        buttons.add(settingsBtn);

        JPanel timerStatePanel = new JPanel(new BorderLayout());
        timerStatePanel.setOpaque(false); //show background color
        timerStatePanel.add(phaseLabel, BorderLayout.NORTH);
        timerStatePanel.add(timeLabel, BorderLayout.CENTER);

        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setOpaque(false);
        centralPanel.add(timerStatePanel, BorderLayout.CENTER);
        centralPanel.add(buttons, BorderLayout.SOUTH);
        setBackground(AppTheme.BG_COLOR);
        add(centralPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateFontSizes();
            }
        });


        swingTimer = new Timer(1000, e -> {
            state = state.tick();
            updateUI();
        });


        playPauseBtn.addActionListener(e -> {
            playPause();
        });

        resetBtn.addActionListener(e -> {
            state = state.reset(settings);
            swingTimer.stop();
            updateUI();
        });

        settingsBtn.addActionListener(e -> {
            SettingsDialog dialog = new SettingsDialog(this, settings);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                settings = new PomodoroSettings(
                        dialog.getFocusMinutes() * 60,
                        dialog.getShortBreakMinutes() * 60,
                        dialog.getLongBreakMinutes() * 60,
                        dialog.getNumberOfSessions()
                );
                updateSettings(settings);
            }
        });
    }

    private String formatTime(int totalSeconds) {
        int min = totalSeconds / 60;
        int sec = totalSeconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

    private String formatPhase(PomodoroPhase phase) {
        switch (phase) {
            case FOCUS:
                return "FOCUS";
            case SHORT_BREAK:
                return "SHORT BREAK";
            case LONG_BREAK:
                return "LONG BREAK";
            default:
                return "FOCUS";
        }
    }

    private void updateSettings(PomodoroSettings settings) {
        if (!state.isRunning()) {
            state = state.reset(settings);
            updateUI();
        }
    }

    private void playPause() {
        if (state.isRunning()) {
            state = state.pause();
            swingTimer.stop();
        } else {
            state = state.start();
            swingTimer.start();
        }
        updateUI();
    }

    private void updateUI() {
        timeLabel.setText(formatTime(state.getRemainingSeconds()));
        phaseLabel.setText(formatPhase(state.getPhase()));
        playPauseBtn.setText(state.isRunning() ? "⏸" : "▶");
    }

    private void updateFontSizes() {
        int h = getHeight();
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, h / 5));
        phaseLabel.setFont(new Font("SansSerif", Font.BOLD, h / 5));
        Font buttonFont = new Font("SansSerif", Font.BOLD, (int)(h /12));
        playPauseBtn.setFont(buttonFont);
        resetBtn.setFont(buttonFont);
        settingsBtn.setFont(buttonFont);
    }
}
