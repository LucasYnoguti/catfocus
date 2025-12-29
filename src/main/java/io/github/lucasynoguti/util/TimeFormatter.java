package io.github.lucasynoguti.util;

public class TimeFormatter {
    public static String formatTimeMinSec(int totalSeconds) {
        int min = totalSeconds / 60;
        int sec = totalSeconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
    public static String formatTimeMinSecH(long totalSeconds) {
        long min = totalSeconds / 60;
        long hor = min/60;
        long sec = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hor, min, sec);
    }
}
