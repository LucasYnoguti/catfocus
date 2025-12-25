package io.github.lucasynoguti.core.database.schema;

public class SettingsTable {
    public static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS settings (
                id INTEGER PRIMARY KEY CHECK (id = 1),
                focus_sec INTEGER,
                short_break_sec INTEGER,
                long_break_sec INTEGER,
                sessions INTEGER
            );
            """;
}
