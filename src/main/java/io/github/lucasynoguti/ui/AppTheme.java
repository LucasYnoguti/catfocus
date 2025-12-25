package io.github.lucasynoguti.ui;

import io.github.lucasynoguti.core.pomodoro.PomodoroPhase;

import java.awt.*;
import java.io.InputStream;

import static io.github.lucasynoguti.core.pomodoro.PomodoroPhase.LONG_BREAK;

public final class AppTheme {
    private AppTheme() {} // impede instance creation

    public static final Color BG_COLOR = Color.WHITE; // white
    public static final Color PRIMARY_COLOR = new Color(65, 100, 74); // light green
    public static final Color ACCENT_COLOR = new Color(233,118,43); // orange
    public static final Color FOCUS_COLOR = new Color(13,71,21);

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
            case FOCUS -> FOCUS_COLOR;
            case SHORT_BREAK -> ACCENT_COLOR;
            case LONG_BREAK -> ACCENT_COLOR;
        };
    }
}
