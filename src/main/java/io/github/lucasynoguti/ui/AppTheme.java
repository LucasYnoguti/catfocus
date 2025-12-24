package io.github.lucasynoguti.ui;

import java.awt.*;
import java.io.InputStream;

public final class AppTheme {
    private AppTheme() {} // impede instance creation

    public static final Color BG_COLOR = new Color(235,225,209); // white
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
}
