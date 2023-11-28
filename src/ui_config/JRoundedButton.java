package ui_config;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class JRoundedButton extends JButton {
    public JRoundedButton(String text, Color fgColor, Color bgColor, int fw, int fs) {
        super(text);
        init(fgColor, bgColor, fw, fs);
    }

    public JRoundedButton(String text, Color fgColor, Color bgColor) {
        super(text);
        init(fgColor, bgColor, 400, 15);
    }

    public JRoundedButton(String text, int fw, int fs) {
        super(text);
        init(Palette.textBlack, Palette.backgroundWhiteAlt, fw, fs);
    }

    public JRoundedButton(String text) {
        super(text);
        init(Palette.textBlack, Palette.backgroundWhiteAlt, 400, 15);
    }

    private void init(Color foregroundColor, Color backgroundColor, int fontWeight, int fontSize) {
        if (fontWeight == 700) {
            setFont(Palette.lineBold(fontSize));
        } else {
            setFont(Palette.lineRegular(fontSize));
        }
        setForeground(foregroundColor);
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);
        setBorder(new RoundedBorder(15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            graphics.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            graphics.setColor(getBackground().brighter());
        } else {
            graphics.setColor(getBackground());
        }

        graphics.fillRoundRect(0, 0, width, height, 7, 7);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();

        int textX = (width - stringBounds.width) / 2;
        int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();

        graphics.setColor(getForeground());
        graphics.setFont(getFont());
        graphics.drawString(getText(), textX, textY);
        graphics.dispose();

        super.paintComponent(g);
    }

    // 둥근 테두리를 그리기 위한 클래스
    class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
