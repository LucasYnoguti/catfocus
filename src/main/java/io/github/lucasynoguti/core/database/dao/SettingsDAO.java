package io.github.lucasynoguti.core.database.dao;

import io.github.lucasynoguti.core.database.DatabaseManager;
import io.github.lucasynoguti.core.pomodoro.PomodoroSettings;

import java.sql.*;


public class SettingsDAO {
    public void saveSettings(PomodoroSettings settings) {
        String sql =
                """
                    INSERT OR REPLACE INTO settings 
                           (id, focus_sec, short_break_sec, long_break_sec, sessions)
                           VALUES (1,?,?,?,?);
                """;

        try (Connection conn = DatabaseManager.getConnection();) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, settings.focusDuration());
            stmt.setInt(2, settings.shortBreakDuration());
            stmt.setInt(3, settings.longBreakDuration());
            stmt.setInt(4, settings.numberOfSessions());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PomodoroSettings load() {
        String sql = "SELECT * FROM settings WHERE id = 1";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if(rs.next())
            {
                return new PomodoroSettings(
                        rs.getInt("focus_sec"),
                        rs.getInt("short_break_sec"),
                        rs.getInt("long_break_sec"),
                        rs.getInt("sessions")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PomodoroSettings.defaultSettings();
    }
}