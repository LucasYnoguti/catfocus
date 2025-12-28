package io.github.lucasynoguti.core.database.schema;

public class SessionTable {
    public static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS sessions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                start_time INTEGER NOT NULL,   -- timestamp (millis)
                created_at DATE DEFAULT (DATE('now', 'localtime')),
                end_time INTEGER,              -- null if aborted session
                planned_duration INTEGER NOT NULL, -- planned duration (s)
                actual_duration INTEGER,          -- real duration (s)
                phase TEXT NOT NULL,           -- FOCUS, SHORT_BREAK, LONG_BREAK
                completed BOOLEAN NOT NULL     -- 1 = concluded, 0 = interrupted
            );
            """;
}
