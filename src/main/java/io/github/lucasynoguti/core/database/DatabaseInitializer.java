package io.github.lucasynoguti.core.database;

import io.github.lucasynoguti.core.database.schema.SessionTable;
import io.github.lucasynoguti.core.database.schema.SettingsTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(SettingsTable.CREATE_TABLE);
            stmt.execute(SessionTable.CREATE_TABLE);
            System.out.println("Database initialized");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
