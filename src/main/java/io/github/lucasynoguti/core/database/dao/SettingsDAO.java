package io.github.lucasynoguti.core.database.dao;
import io.github.lucasynoguti.core.database.DatabaseManager;
import io.github.lucasynoguti.core.pomodoro.PomodoroSettings;

import java.sql.*;


public class SettingsDAO {
    public void saveSettings(PomodoroSettings settings){
        String sql =
                "INSERT OR REPLACE INTO settings (id, focus_sec, short_break_sec, long_break_sec, sessions) VALUES (1," + settings.focusDuration() + "," + settings.shortBreakDuration() + "," + settings.longBreakDuration() + "," + settings.numberOfSessions() + ")";
        try (Connection conn = DatabaseManager.getConnection();) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PomodoroSettings load(){
        String sql = "SELECT * FROM settings WHERE id = 1";
        ResultSet rs = null;
        try (Connection conn = DatabaseManager.getConnection();) {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int id = rs.getInt("id");
            int focusDuration = rs.getInt("focus_sec");
            int shortBreakDuration = rs.getInt("short_break_sec");
            int longBreakDuration = rs.getInt("long_break_sec");
            int numberOfSessions = rs.getInt("sessions");
            return new PomodoroSettings(focusDuration, shortBreakDuration, longBreakDuration, numberOfSessions);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
       return new PomodoroSettings(25*60, 5*60, 15*60, 4);
    }
}