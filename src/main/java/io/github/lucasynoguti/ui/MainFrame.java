package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.pomodoro.PomodoroController;
import io.github.lucasynoguti.core.pomodoro.PomodoroSettings;
import io.github.lucasynoguti.core.pomodoro.PomodoroState;
import io.github.lucasynoguti.ui.pomodoro.AppTheme;
import io.github.lucasynoguti.ui.pomodoro.SettingsDialog;
import io.github.lucasynoguti.ui.timer.PomodoroPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;


public class MainFrame extends JFrame {
    private final PomodoroPanel pomodoroPanel;
    private final PomodoroController controller;

    public MainFrame(PomodoroController controller) {
        setupWindowProperties();
        this.controller = controller;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.onAppClose();
                dispose();
                System.exit(0);
            }
        });

        //view
        pomodoroPanel = new PomodoroPanel();
        add(pomodoroPanel);

        //actions
        pomodoroPanel.getPlayPauseBtn().addActionListener(e -> controller.playPause());
        pomodoroPanel.getResetBtn().addActionListener(e -> controller.reset());
        pomodoroPanel.getSettingsBtn().addActionListener(e -> openSettings());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                pomodoroPanel.updateFontSizes(getHeight());
            }
        });

        updateView();
    }
    private void setupWindowProperties(){
        setTitle("CatFocus");
        setSize(500, 300);
        setMinimumSize(new Dimension(300, 200));
        setLayout(new GridBagLayout());
        getContentPane().setBackground(AppTheme.PRIMARY_COLOR);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        URL iconURL = getClass().getResource("/images/icon/demonio.png");
        if (iconURL != null) {
            setIconImage(new ImageIcon(iconURL).getImage());
        }
    }
    private void openSettings() {
        SettingsDialog dialog = new SettingsDialog(this, controller.getSettings());
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            PomodoroSettings newSettings = new PomodoroSettings(
                    dialog.getFocusMinutes() * 60,
                    dialog.getShortBreakMinutes() * 60,
                    dialog.getLongBreakMinutes() * 60,
                    dialog.getNumberOfSessions()
            );
            controller.updateSettings(newSettings);
        }
    }

    public void updateView() {
        PomodoroState state = controller.getState();

        getContentPane().setBackground(AppTheme.getColorForPhase(state.phase()));

        pomodoroPanel.updateTime(formatTime(state.remainingSeconds()));
        pomodoroPanel.updatePhase(state.phase());
        pomodoroPanel.updateButtons(state.running(), state.phase());

    }

    //helpers
    private String formatTime(int totalSeconds) {
        int min = totalSeconds / 60;
        int sec = totalSeconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
}