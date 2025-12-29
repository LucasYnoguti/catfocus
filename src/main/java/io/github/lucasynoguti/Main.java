package io.github.lucasynoguti;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;
import io.github.lucasynoguti.core.database.DatabaseInitializer;
import io.github.lucasynoguti.core.database.dao.SessionDAO;
import io.github.lucasynoguti.core.pomodoro.PomodoroSettings;
import io.github.lucasynoguti.core.database.dao.SettingsDAO;
import io.github.lucasynoguti.ui.theme.AppTheme;
import io.github.lucasynoguti.ui.MainFrame;
import io.github.lucasynoguti.core.pomodoro.PomodoroController;
import io.github.lucasynoguti.util.SoundPlayer;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        UIManager.put("ComboBox.selectionBackground", AppTheme.PRIMARY_COLOR);
        UIManager.put("List.selectionBackground", AppTheme.PRIMARY_COLOR);
        UIManager.put("Component.focusColor", AppTheme.PRIMARY_COLOR);

        //initializing database
        DatabaseInitializer.initialize();
        SettingsDAO settingsDAO = new SettingsDAO();
        SessionDAO sessionDAO = new SessionDAO();
        PomodoroSettings settings = settingsDAO.load();
        PomodoroController controller = new PomodoroController(settings, settingsDAO, sessionDAO);
        //preloading sounds to play faster
        SoundPlayer.loadSounds("/sounds/ding.wav");

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(controller);
            controller.setOnUpdateCallback(mainFrame::updateView);
            mainFrame.setVisible(true);
        });
    }
}