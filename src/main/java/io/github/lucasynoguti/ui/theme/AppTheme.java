package io.github.lucasynoguti.ui.theme;

import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;

import java.awt.*;
import java.io.InputStream;

public final class AppTheme {
    private AppTheme() {} // impede instance creation
    public static final Color WHITE = Color.WHITE;
    public static final Color PRIMARY_COLOR = new Color(65, 100, 74); // light green
    public static final Color ORANGE = new Color(212, 139, 95); // orange
    public static final Color DARK_GREEN = new Color(137, 112, 79);

    public static final Font MAIN_FONT;

    static{
        Font font;
        try( InputStream is = AppTheme.class.getResourceAsStream("/fonts/TitanOne-Regular.ttf") ){
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        }
        catch(Exception e){
            e.printStackTrace();
            font = new Font("Arial", Font.PLAIN, 12);
        }
        MAIN_FONT = font;
    }

    public static Color getColorForPhase(PomodoroPhase phase) {
        return switch (phase) {
            case FOCUS -> PRIMARY_COLOR;
            case SHORT_BREAK -> DARK_GREEN;
            case LONG_BREAK -> ORANGE;
        };
    }
}
