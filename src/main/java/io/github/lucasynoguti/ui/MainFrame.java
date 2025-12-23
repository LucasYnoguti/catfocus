package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;
import io.github.lucasynoguti.core.pomodoro.PomodoroState;
import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame{
    Integer[] focusOptions = {25, 15, 20, 30, 40, 50, 60};
    Integer[] shortBreakOptions = {5, 7, 10, 15};
    private JLabel timeLabel;
    private JLabel phaseLabel;
    JButton playPauseBtn;
    JButton resetBtn;
    private PomodoroState state;
    private Timer swingTimer;
    private JComboBox<Integer> focusDropdown;
    private JComboBox<Integer> shortBreakDropdown;
    private JLabel focusLabel;
    private JLabel shortBreakLabel;


    public MainFrame() {
        setTitle("CatFocus");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        focusDropdown = new JComboBox<>(focusOptions);
        shortBreakDropdown = new JComboBox<>(shortBreakOptions);
        focusLabel = new JLabel("Focus Duration:");
        shortBreakLabel = new JLabel("Short Break Duration:");

        JPanel settingsPanel = new JPanel();
        settingsPanel.add(focusLabel);
        settingsPanel.add(focusDropdown);
        settingsPanel.add(shortBreakLabel);
        settingsPanel.add(shortBreakDropdown);

        add(settingsPanel, BorderLayout.NORTH);

        state = new PomodoroState(25*60, false, PomodoroPhase.FOCUS, 25*60, 5*60, 15*60);
        phaseLabel = new JLabel(formatPhase(state.getPhase()), SwingConstants.CENTER);
        phaseLabel.setFont(new Font("Arial", Font.BOLD, 30));

        timeLabel = new JLabel(formatTime(state.getRemainingSeconds()), SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 40));

        playPauseBtn = new JButton("Play");
        resetBtn = new JButton("Reset");



        JPanel buttons = new JPanel();
        buttons.add(playPauseBtn);
        buttons.add(resetBtn);

        JPanel timerStatePanel = new JPanel(new BorderLayout());
        timerStatePanel.add(phaseLabel, BorderLayout.NORTH);
        timerStatePanel.add(timeLabel, BorderLayout.CENTER);
        add(timerStatePanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        focusDropdown.addActionListener(e -> updateSettings());
        shortBreakDropdown.addActionListener(e -> updateSettings());

        swingTimer = new Timer(1000, e -> {
            state = state.tick();
            updateUI();
        });


        playPauseBtn.addActionListener(e -> { playPause(); });

        resetBtn.addActionListener(e -> {
            int focusMinutes = (Integer) focusDropdown.getSelectedItem();
            int shortBreakMinutes = (Integer) shortBreakDropdown.getSelectedItem();
            state = state.reset(focusMinutes * 60, shortBreakMinutes * 60);
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

    private void updateSettings() {
        int focusSeconds = (Integer) focusDropdown.getSelectedItem() * 60;
        int shortBreakSeconds = (Integer) shortBreakDropdown.getSelectedItem() * 60;

        // Se o timer estiver parado, resetamos para o novo tempo de foco
        if (!state.isRunning()) {
            state = state.reset(focusSeconds, shortBreakSeconds);
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
        phaseLabel.setText(state.getPhase().toString());
        playPauseBtn.setText(state.isRunning() ? "Pause" : "Play");
    }
}
