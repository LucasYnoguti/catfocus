package io.github.lucasynoguti.ui;
import io.github.lucasynoguti.ui.components.AppButton;

import javax.swing.*;
import java.awt.*;

public class SideBarPanel extends JPanel {
    private final JButton pomodoroBtn;
    private final JButton statsBtn;
    private final JButton catsBtn;
    private int lastFontSize = -1;
    public SideBarPanel(
            Runnable onPomodoro,
            Runnable onStats,
            Runnable onCats
    ) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);

        pomodoroBtn = createButton("Pomodoro", onPomodoro);
        statsBtn = createButton("Stats", onStats);
        catsBtn = createButton("Cats", onCats);

        add(Box.createVerticalStrut(20));
        add(pomodoroBtn);
        add(statsBtn);
        add(catsBtn);
        add(Box.createVerticalGlue());
    }

    private JButton createButton(String text, Runnable action) {
        JButton btn = new AppButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, btn.getPreferredSize().height));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        btn.addActionListener(e -> action.run());
        return btn;
    }

    public void onResize(int frameHeight, int frameWidth) {
        updateFonts(frameHeight, frameWidth);
        updateWidth();
    }

    private void updateFonts(int frameHeight, int frameWidth) {
        int min = Math.min(frameHeight, frameWidth);
        int newFontSize = Math.max(18, min / 20);

        if (newFontSize == lastFontSize) return;

        lastFontSize = newFontSize;
        Font f = new Font("SansSerif", Font.PLAIN, newFontSize);

        for (JButton btn : new JButton[]{pomodoroBtn, statsBtn, catsBtn}) {
            btn.setFont(f);
        }

        revalidate();
        repaint();
    }

    private void updateWidth() {
        int maxWidth = 0;

        for (JButton btn : new JButton[]{pomodoroBtn, statsBtn, catsBtn}) {
            maxWidth = Math.max(maxWidth, btn.getPreferredSize().width);
        }

        Insets insets = getInsets();
        int totalWidth = maxWidth + insets.left + insets.right;

        Dimension newSize = new Dimension(totalWidth, getHeight());
        setPreferredSize(newSize);

        revalidate();

    }
}

