package io.github.lucasynoguti.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:" + System.getProperty("user.home") + "/catfocus.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        String sql = """
                CREATE TABLE IF NOT EXISTS settings (
                    id INTEGER PRIMARY KEY CHECK (id = 1),
                    focus_sec INTEGER,
                    short_break_sec INTEGER,
                    long_break_sec INTEGER,
                    sessions INTEGER
                );
                """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("DB initialized in : " + URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
