package gui;

import mgr.MusicMgr;
import stream.Album;
import stream.Music;
import stream.Playlist;
import ui_config.JRoundedButton;
import ui_config.JText;
import ui_config.Palette;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/* 박영호
 * TODO JSlider 디자인?
 * */
public class PlayerPanel extends JPanel implements ListSelectionListener {

    JPanel topPane;
    JPanel midPane;
    JPanel botPane;
    JLabel showImg;
    JText musicName;
    JText musicInfo;
    JPanel musicList;
    DefaultListModel<Music> listModel;
    int selectedIndex = -1;

    public PlayerPanel() {
        this(MusicMgr.getInstance().mList);
    }

    public PlayerPanel(ArrayList<Music> mList) {
        listModel = new DefaultListModel<>();
        for(Music music : mList) {
            listModel.addElement(music);
        }
        selectedIndex = 0;
        setLayout(new BorderLayout());
        setTopPane();
        setMidPane();
        setBotPane();
        add(topPane, BorderLayout.NORTH);
        add(midPane, BorderLayout.CENTER);
        add(botPane, BorderLayout.SOUTH);
        setBackgroundColor("res/002.jpg");
        musicChange(selectedIndex);
        musicTimerSetup();
        playMusic();
        PlayerPanel pp = this;
        Timer sizeTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pp.getWidth() > 1333 && pp.getHeight() > 900) {
                    setFullScreen();
                }
                else {
                    setSmallScreen();
                }
            }
        });
        sizeTimer.start();
    }

    boolean isFullScreen;
    public void setFullScreen() {
        isFullScreen = true;
        blankLeft.setText("　　　　　　　　　　　　　　　　　　　");
        showImg.setIcon(resizingImage("res/" + jList.getModel().getElementAt(selectedIndex).getInfo(3), 500, 500));
        //showImg.setPreferredSize(new Dimension(500,500));
        scrollPane.setPreferredSize(new Dimension(900, 700));
        scrollPane.revalidate();
        scrollPane.repaint();
    }
    public void setSmallScreen() {
        isFullScreen = false;
        blankLeft.setText("　　　　　　　　　　　　　");
        showImg.setIcon(new ImageIcon("res/" + jList.getModel().getElementAt(selectedIndex).getInfo(3)));
        scrollPane.setPreferredSize(new Dimension(590, 490));
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    void setMusicImage(String path) {
        if(isFullScreen)
            showImg.setIcon(resizingImage(path, 500, 500));
        else
            showImg.setIcon(new ImageIcon(path));
    }

    /** 배경색, 앨범커버, 음악정보를 갱신함 */
    void musicChange(int index) {
        Music music = jList.getModel().getElementAt(index);
        String imgPath = "res/" + music.getInfo(3);
//    	showImg.setIcon(new ImageIcon(imgPath));
        setMusicImage(imgPath);
        setBackgroundColor(imgPath);
        musicName.setText(music.getInfo(1));
        musicInfo.setText(music.getInfo(5));
        timeBar.setValue(0);
        timeBar.setMaximum(Integer.parseInt(music.getInfo(7)));
        jList.setSelectedIndex(selectedIndex);
    }

    void setBackgroundColor(String imgPath) {
        Color color = getImageColor(imgPath);
        this.setBackground(color);
        topPane.setBackground(color);
        midPane.setBackground(color);
        midTopPane.setBackground(color);
        midMusicInfo.setBackground(color);
        lPane.setBackground(color);
        musicList.setBackground(color);
        jList.setBackground(color);
        scrollPane.setBackground(color);
        textPane.setBackground(color);
        blankPane.setBackground(color);
        timeLinePane.setBackground(color);
        buttonPane.setBackground(color);
        timeBar.setBackground(color);
        back.setBackground(color);
        playListItemRenderer.setBackground(color);
        Color selectedColor = getSelectedImageColor(imgPath);
        jList.setSelectionBackground(selectedColor);
        if((color.getRed() + color.getGreen() + color.getBlue()) < 300) {
            back.setForeground(Palette.backgroundWhite);
            playListItemRenderer.setTextColor(Palette.backgroundWhite);
            musicList.setForeground(Palette.backgroundWhite);
            musicName.setForeground(Palette.backgroundWhite);
            musicInfo.setForeground(Palette.backgroundWhite);
            setScrollBarUI(color, true);
        }
        else {
            back.setForeground(Palette.textBlack);
            playListItemRenderer.setTextColor(Palette.textBlack);
            jList.setForeground(Palette.textBlack);
            musicList.setForeground(Palette.textBlack);
            musicName.setForeground(Palette.textBlack);
            musicInfo.setForeground(Palette.textBlack);
            setScrollBarUI(color, false);
        }
    }

    void setScrollBarUI(Color color, boolean isBrightBackground) {
        scrollBarUI = new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JRoundedButton button = new JRoundedButton("▼");
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JRoundedButton button = new JRoundedButton("▲");
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
            @Override
            protected void configureScrollBarColors() {
                int nR, nG, nB;
                if(!isBrightBackground) {
                    nR = color.getRed() - 80;
                    nG = color.getGreen() - 80;
                    nB = color.getBlue() - 80;
                    if(nR <= 0) nR = 0;
                    if(nG <= 0) nG = 0;
                    if(nB <= 0) nB = 0;
                }
                else {
                    nR = color.getRed() + 80;
                    nG = color.getGreen() + 80;
                    nB = color.getBlue() + 80;
                    if(nR >= 255) nR = 255;
                    if(nG >= 255) nG = 255;
                    if(nB >= 255) nB = 255;
                }
                Color c = new Color(nR, nG, nB);
                this.thumbColor = c;
                this.trackColor = color;
            }
        };
        scrollPane.getVerticalScrollBar().setUI(scrollBarUI);
    }

    Color getImageColor(String imgPath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            return Palette.backgroundWhite;
        }
        int width = img.getWidth();
        int height = img.getHeight();
        int evgR = 0;
        int evgG = 0;
        int evgB = 0;

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Color c = new Color(img.getRGB(i, j));
                evgR += c.getRed();
                evgG += c.getGreen();
                evgB += c.getBlue();
            }
        }
        int count = width*height;
        evgR /= count;
        evgG /= count;
        evgB /= count;
        Color color = new Color(evgR, evgG, evgB);
        return color;
    }

    Color getSelectedImageColor(String imgPath) {
        Color color = getImageColor(imgPath);
        int sR = color.getRed() + 50;
        int sG = color.getGreen() + 50;
        int sB = color.getBlue() + 50;
        if(sR >= 255) sR = 255;
        if(sG >= 255) sG = 255;
        if(sB >= 255) sB = 255;
        return new Color(sR, sG, sB);
    }

    JRoundedButton back;
    void setTopPane() {
        topPane = new JPanel(new BorderLayout());
        back = new JRoundedButton("뒤로가기");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((CardLayout)GUIMain.mainPanel.getLayout()).show(GUIMain.mainPanel, "home");
            }
        });
        back.addMouseListener(new BackButtonMouseListener());
        topPane.add(back, BorderLayout.WEST);
    }

    JPanel lPane;
    JList<Music> jList;
    JScrollPane scrollPane;
    JPanel textPane;
    JPanel blankPane;
    JPanel midTopPane;
    JPanel midMusicInfo;
    PlayListItemRenderer playListItemRenderer;
    BasicScrollBarUI scrollBarUI;
    JLabel blankLeft;
    JLabel blankRight;
    void setMidPane() {
        midPane = new JPanel();
        GridLayout midGrid = new GridLayout(1, 2);
        midPane.setLayout(midGrid);

        blankLeft = new JLabel("　　　　　　　　　　　　　");
        blankRight = new JLabel("　　　　　　　　　　　　　");
        lPane = new JPanel(new BorderLayout());

        /* 좌측 패널 셋업*/
        midTopPane = new JPanel(new BorderLayout());
        blankPane = new JPanel();
        blankPane.setPreferredSize(new Dimension(50, 60));
        ImageIcon rawImg = new ImageIcon("res/002.jpg");
        showImg = new JLabel();
        showImg.setIcon(rawImg);
        midTopPane.add(blankPane, BorderLayout.NORTH);
        midTopPane.add(showImg, BorderLayout.CENTER);

        textPane = new JPanel(new BorderLayout());
        musicName = new JText("MusicName", 700, 30);
        musicInfo = new JText("MusicInfo", 400, 30);
        textPane.add(musicName, BorderLayout.NORTH);
        textPane.add(musicInfo, BorderLayout.SOUTH);

        midMusicInfo = new JPanel(new BorderLayout());
        midMusicInfo.add(midTopPane, BorderLayout.NORTH);
        midMusicInfo.add(textPane, BorderLayout.SOUTH);

        lPane.add(blankLeft, BorderLayout.WEST);
        lPane.add(midMusicInfo, BorderLayout.CENTER);
        lPane.add(blankRight, BorderLayout.EAST);

        /* 우측 패널 셋업 */
        musicList = new JPanel();
        jList = new JList<>(listModel);
        jList.addListSelectionListener(this);
        //jList.setCellRenderer(new ItemRenderer());
        playListItemRenderer = new PlayListItemRenderer();
        jList.setCellRenderer(playListItemRenderer);
        jList.setBorder(BorderFactory.createEmptyBorder());

        scrollPane = new JScrollPane(jList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(590, 490));
        scrollBarUI = new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JRoundedButton button = new JRoundedButton("▼");
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JRoundedButton button = new JRoundedButton("▲");
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Palette.primaryGray;
                this.trackColor = Palette.backgroundWhite;
            }
        };
        scrollPane.getVerticalScrollBar().setUI(scrollBarUI);
        musicList.add(scrollPane, BorderLayout.CENTER);
        midPane.add(lPane);
        midPane.add(musicList);
    }

    JPanel timeLinePane;
    JPanel buttonPane;
    JSlider timeBar;
    JLabel center;
    void setBotPane() {
        timeLinePane = new JPanel();
        timeBar = new JSlider(0, 1, 0);
        timeBar.setPreferredSize(new Dimension(650, 50));
        timeLinePane.add(timeBar);
        timeLinePane.setPreferredSize(new Dimension(200, 50));

        buttonPane = new JPanel(new FlowLayout());
        JLabel left = new JLabel();
        center = new JLabel();
        JLabel right = new JLabel();
        JLabel blank1 = new JLabel();
        blank1.setPreferredSize(new Dimension(50, 50));
        JLabel blank2 = new JLabel();
        blank2.setPreferredSize(new Dimension(50, 50));
        left.setIcon(resizingImage("res/004.png", 100, 100));
        center.setIcon(resizingImage("res/005.png", 100, 100));
        right.setIcon(resizingImage("res/003.png", 100, 100));
        left.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                previousAction();
            }
        });
        center.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                playAction();
            }
        });
        right.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                nextAction();
            }
        });
        buttonPane.add(left);
        buttonPane.add(blank1);
        buttonPane.add(center);
        buttonPane.add(blank2);
        buttonPane.add(right);
        buttonPane.setPreferredSize(new Dimension(200, 150));

        botPane = new JPanel(new BorderLayout());
        botPane.add(timeLinePane, BorderLayout.NORTH);
        botPane.add(buttonPane, BorderLayout.CENTER);
    }

    Timer timer;
    void musicTimerSetup() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timeBar.getMaximum() == timeBar.getValue()) {
                    nextAction();
                }
                timeBar.setValue(timeBar.getValue() + 1);
            }
        });
    }
    void playMusic() {
        timer.start();
    }
    void stopMusic() {
        timer.stop();
    }

    void previousAction() {
        if(selectedIndex <= 0) {
            JOptionPane.showMessageDialog(null, "이전 곡이 없습니다.");
        }
        else {
            musicChange(--selectedIndex);
        }
    }
    void playAction() {
        if(timer.isRunning()) {
            System.out.println("정지");
            // TODO 재생 아이콘 추가해서 이미지 경로 수정 필요
            center.setIcon(resizingImage("res/007.png", 100, 100));
            stopMusic();
        }
        else {
            System.out.println("재생");
            center.setIcon(resizingImage("res/005.png", 100, 100));
            playMusic();
        }
    }
    void nextAction() {
        if(selectedIndex >= (jList.getModel().getSize() - 1)) {
            JOptionPane.showMessageDialog(null, "다음 곡이 없습니다.");
            selectedIndex = 0;
            musicChange(selectedIndex);
        }
        else {
            musicChange(++selectedIndex);
        }
    }

    ImageIcon resizingImage(String imgPath, int width, int height) {
        ImageIcon rawImg = new ImageIcon(imgPath);
        Image image = rawImg.getImage();
        image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon resizeImg = new ImageIcon(image);
        return resizeImg;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        selectedIndex = jList.getSelectedIndex();
        musicChange(selectedIndex);
    }

    @SuppressWarnings("serial")
    class PlayListItemRenderer<T> extends ItemRenderer<T> {
        public PlayListItemRenderer() {
            super();
        }
        public void setTextColor(Color color) {
            titleLabel.setForeground(color);
            infoLabel.setForeground(color);
        }
        @Override
        public Component getListCellRendererComponent(JList<? extends T> list, T item, int index, boolean isSelected, boolean cellHasFocus) {
            Music music = (Music)item;
            ImageIcon imageIcon = new ImageIcon("res/" + music.getImagePath());
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

    class BackButtonMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }
        @Override
        public void mousePressed(MouseEvent e) {
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            Color c = getSelectedImageColor("res/" + jList.getModel().getElementAt(selectedIndex).getImagePath());
            JRoundedButton button = (JRoundedButton)e.getSource();
            button.setBackground(c);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            Color c = getImageColor("res/" + jList.getModel().getElementAt(selectedIndex).getImagePath());
            JRoundedButton button = (JRoundedButton)e.getSource();
            button.setBackground(c);
        }
    }
}