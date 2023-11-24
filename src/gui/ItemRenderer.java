package gui;

import stream.Music;
import ui_config.JText;

import javax.swing.*;
import java.awt.*;

class ItemRenderer extends JPanel implements ListCellRenderer<Music> {
    private JPanel itemPanel;
    private final JLabel imageLabel;
    private final JText titleLabel;
    private final JText infoLabel;

    public ItemRenderer() {
        setLayout(new GridLayout(1, 1));

        itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));

        ImageIcon imageIcon = new ImageIcon("res/002.jpg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        imageLabel = new JLabel(scaledImageIcon);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(2, 1));
        textPanel.setBackground(new Color(0,0,0,0));

        titleLabel = new JText("");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoLabel = new JText("");

        textPanel.add(titleLabel);
        textPanel.add(infoLabel);

        itemPanel.add(imageLabel);
        itemPanel.add(textPanel);
        add(itemPanel);
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends Music> list, Music music, int index, boolean isSelected, boolean cellHasFocus) {

        ImageIcon imageIcon = new ImageIcon("res/"+music.getImagePath());
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        titleLabel.setText(music.getInfo(1));
        infoLabel.setText(music.getInfo(5) + " · " + music.getInfo(6));
        imageLabel.setIcon(scaledImageIcon);

        if (isSelected) {
            itemPanel.setBackground(list.getSelectionBackground());
            itemPanel.setForeground(list.getSelectionForeground());
        } else {
            itemPanel.setBackground(list.getBackground());
            itemPanel.setForeground(list.getForeground());
        }

        return this;
    }
}
