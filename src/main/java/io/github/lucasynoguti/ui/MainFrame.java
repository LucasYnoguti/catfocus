package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;
import io.github.lucasynoguti.core.pomodoro.PomodoroState;
import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame{
    private JLabel timeLabel;
    private JLabel phaseLabel;
    JButton playPauseBtn;
    JButton resetBtn;
    private PomodoroState state;
    private final int totalSeconds =  5;
    private Timer swingTimer;

    public MainFrame() {
        setTitle("CatFocus");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        state = new PomodoroState(totalSeconds, false, PomodoroPhase.FOCUS);

        phaseLabel = new JLabel(formatPhase(state.getPhase()), SwingConstants.CENTER);
        phaseLabel.setFont(new Font("Arial", Font.BOLD, 30));

        timeLabel = new JLabel(formatTime(state.getRemainingSeconds()), SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 40));

        playPauseBtn = new JButton("Play");
        resetBtn = new JButton("Reset");



        JPanel buttons = new JPanel();
        buttons.add(playPauseBtn);
        buttons.add(resetBtn);

        add(timeLabel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        add(phaseLabel, BorderLayout.NORTH);

        swingTimer = new Timer(1000, e -> {
            state = state.tick();

            timeLabel.setText(formatTime(state.getRemainingSeconds()));
            phaseLabel.setText(formatPhase(state.getPhase()));
            playPauseBtn.setText(state.isRunning() ? "Pause" : "Play");
        });


        playPauseBtn.addActionListener(e -> { playPause(); });

        resetBtn.addActionListener(e -> {
            state = state.reset(totalSeconds);
            swingTimer.stop();
            updateUI();
        });
    }
    private String formatTime(int totalSeconds) {
        int min = totalSeconds / 60;
        int sec = totalSeconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

    private String formatPhase(PomodoroPhase phase) {
        switch (phase) {
            case FOCUS: return "FOCUS";
            case SHORT_BREAK: return "SHORT BREAK";
            default: return "FOCUS";
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
        phaseLabel.setText(state.getPhase().toString());
        playPauseBtn.setText(state.isRunning() ? "Pause" : "Play");
    }


}
