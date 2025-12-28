package io.github.lucasynoguti.core.database.dao;

import io.github.lucasynoguti.core.database.DatabaseManager;
import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAO {
    public long beginSession(PomodoroPhase phase, int plannedDuration, long startTime) {
        String sql =
                """
                    INSERT INTO sessions (start_time, planned_duration, phase, completed) values (?,?,?,0);
                """;

        try (Connection conn = DatabaseManager.getConnection();) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setLong(1,startTime);
            stmt.setInt(2,plannedDuration);
            stmt.setString(3, phase.name());
            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Failed to get session id");
    }

    public void finishSession(long sessionId, boolean completed, int actualDurationSeconds, long endTime) {
        String sql = """
            UPDATE sessions
            SET
                end_time = ?,
                actual_duration = ?,
                completed = ?
            WHERE id = ?
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, endTime);
            stmt.setInt(2, actualDurationSeconds);
            stmt.setInt(3, completed ? 1 : 0);
            stmt.setLong(4, sessionId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao finalizar sessão", e);
        }
    }


}
