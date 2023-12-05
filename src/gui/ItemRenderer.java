package gui;

import stream.Album;
import stream.Music;
import stream.Playlist;
import ui_config.JText;

import javax.swing.*;
import java.awt.*;

class ItemRenderer<T> extends JPanel implements ListCellRenderer<T> {
    protected JPanel itemPanel;
    protected final JLabel imageLabel;
    protected final JText titleLabel;
    protected final JText infoLabel;

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
        infoLabel = new JText("");

        textPanel.add(titleLabel);
        textPanel.add(infoLabel);

        itemPanel.add(imageLabel);
        itemPanel.add(textPanel);
        add(itemPanel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends T> list, T item, int index, boolean isSelected, boolean cellHasFocus) {
        if (item instanceof Music) {
            Music music = (Music) item;
            setMusicData(music, isSelected, list.getSelectionBackground(), list.getSelectionForeground());
        } else if (item instanceof Album) {
            Album album = (Album) item;
            setAlbumData(album, isSelected, list.getSelectionBackground(), list.getSelectionForeground());
        } else if (item instanceof Playlist) {
            Playlist playlist = (Playlist) item;
            setPlaylistData(playlist, isSelected, list.getSelectionBackground(), list.getSelectionForeground());
        }

        return this;
    }

    private void setMusicData(Music music, boolean isSelected, Color selectionBackground, Color selectionForeground) {
        ImageIcon imageIcon = new ImageIcon("res/" + music.getImagePath());
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        titleLabel.setText(music.getInfo(1));
        infoLabel.setText(music.getInfo(5) + " · " + music.getInfo(6));
        imageLabel.setIcon(scaledImageIcon);

        if (isSelected) {
            itemPanel.setBackground(selectionBackground);
            itemPanel.setForeground(selectionForeground);
        } else {
            itemPanel.setBackground(UIManager.getColor("List.background"));
            itemPanel.setForeground(UIManager.getColor("List.foreground"));
        }
    }
    private void setAlbumData(Album album, boolean isSelected, Color selectionBackground, Color selectionForeground) {
        ImageIcon imageIcon = new ImageIcon("res/" + album.getImagePath());
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        titleLabel.setText(album.getUiTexts()[0]);
        infoLabel.setText(album.getUiTexts()[1]);
        imageLabel.setIcon(scaledImageIcon);

        if (isSelected) {
            itemPanel.setBackground(selectionBackground);
            itemPanel.setForeground(selectionForeground);
        } else {
            itemPanel.setBackground(UIManager.getColor("List.background"));
            itemPanel.setForeground(UIManager.getColor("List.foreground"));
        }
    }
    private void setPlaylistData(Playlist playlist, boolean isSelected, Color selectionBackground, Color selectionForeground) {
        ImageIcon imageIcon = new ImageIcon("res/" + "006.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        titleLabel.setText(playlist.getUiTexts()[2]);
        infoLabel.setText(playlist.getUiTexts()[3] + " "
                + playlist.getUiTexts()[4] + "곡");
        imageLabel.setIcon(scaledImageIcon);

        if (isSelected) {
            itemPanel.setBackground(selectionBackground);
            itemPanel.setForeground(selectionForeground);
        } else {
            itemPanel.setBackground(UIManager.getColor("List.background"));
            itemPanel.setForeground(UIManager.getColor("List.foreground"));
        }
    }
}