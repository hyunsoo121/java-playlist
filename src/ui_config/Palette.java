package ui_config;

import java.awt.*;

public class Palette {
    public static Color backgroundWhite = Color.decode("#fefeff");
    public static Color backgroundWhiteAlt = Color.decode("#eeeef0");
    public static Color primaryBlue = Color.decode("#5a88f6");
    public static Color primaryBlueAlt = Color.decode("#2e6ce8");
    public static Color primaryGray = Color.decode("#efefef");
    public static Color primaryGrayAlt = Color.decode("#dedee0");
    public static Color textBlack = Color.decode("#222224");
    public static Color textBlackAlt = Color.decode("#525252");
    public static Color transparent = new Color(0, 0, 0, 0);

    public static Font lineBold(int size) {
        return new Font("LINE Seed Sans KR Bold", Font.PLAIN, size);
    }

    public static Font lineRegular(int size) {
        return new Font("LINE Seed Sans KR Regular", Font.PLAIN, size);
    }
}
