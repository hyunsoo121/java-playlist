package ui_config;

import javax.swing.*;
import java.awt.*;

public class JText extends JLabel {
    public JText(String text, Color textColor, int fw, int fs) {
        super(text);
        init(textColor, fw, fs);
    }

    public JText(String text, int fw, int fs) {
        super(text);
        init(Palette.textBlack, fw, fs);
    }

    public JText(String text) {
        super(text);
        init(Palette.textBlack, 400, 15);
    }

    private void init(Color color, int fontWeight, int fontSize) {
        if (fontWeight == 700) {
            setFont(Palette.lineBold(fontSize));
        } else {
            setFont(Palette.lineRegular(fontSize));
        }
        setForeground(color);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }
}
