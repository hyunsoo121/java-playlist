package gui;

import mgr.AlbumMgr;
import mgr.MusicMgr;
import mgr.PlaylistMgr;
import stream.Album;
import stream.Music;
import stream.Playlist;
import stream.Stream;
import ui_config.JRoundedButton;
import ui_config.JText;
import ui_config.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ListPanel extends JPanel {
    private ArrayList<Integer> selectedMusicIndices = new ArrayList<>();
    public ListPanel(int page){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Palette.backgroundWhite);

        switch (page) {
            case 1:
                JPanel topPanel = new JPanel(new BorderLayout());
                topPanel.setBackground(Palette.backgroundWhite);

                JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JText text1 = new JText("콜론차트", 700, 50);
                leftPanel.add(text1);

                leftPanel.setBackground(Palette.backgroundWhite);
                leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

                topPanel.add(leftPanel, BorderLayout.NORTH);


                JPanel buttonPanel = new JPanel(new BorderLayout());
                buttonPanel.setBackground(Palette.backgroundWhite);

                JPanel flowLayoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                flowLayoutPanel.setBackground(Palette.backgroundWhite);
                flowLayoutPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

                JRoundedButton rb1 = new JRoundedButton("인기순", Palette.backgroundWhite, Palette.primaryBlue);
                flowLayoutPanel.add(rb1);

                JRoundedButton rb2 = new JRoundedButton("최신순");
                flowLayoutPanel.add(rb2);

                JRoundedButton rb3 = new JRoundedButton("플레이리스트에 추가");
                flowLayoutPanel.add(rb3);

                buttonPanel.add(flowLayoutPanel, BorderLayout.SOUTH);

                add(topPanel);
                add(buttonPanel);

                JPanel testList = new JPanel();
                testList.setBackground(Palette.backgroundWhite);

                DefaultListModel<Music> listModel = new DefaultListModel<>();
                for (Music music : MusicMgr.getInstance().mList) {
                    listModel.addElement(music);
                }

                JList<Music> jList = new JList<>(listModel);
                jList.setCellRenderer(new ItemRenderer<Music>());
                jList.setBorder(BorderFactory.createEmptyBorder());
                jList.setBackground(Palette.backgroundWhite);
                jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

                JScrollPane scrollPane = new JScrollPane(jList);
                scrollPane.setBackground(Palette.backgroundWhite);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());
                scrollPane.setPreferredSize(new Dimension(1500,750));

                testList.setLayout(new FlowLayout(FlowLayout.LEFT));
                testList.add(scrollPane);
                add(testList);


                rb1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Stream.sortList(1);
                        updateList(testList);
                        rb2.setBackground(Palette.primaryGray);
                        rb2.setForeground(Palette.textBlack);
                        rb1.setBackground(Palette.primaryBlue);
                        rb1.setForeground(Palette.backgroundWhite);


                    }
                });
                rb2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Stream.sortList(2);
                        updateList(testList);
                        rb1.setBackground(Palette.primaryGray);
                        rb1.setForeground(Palette.textBlack);
                        rb2.setBackground(Palette.primaryBlue);
                        rb2.setForeground(Palette.backgroundWhite);
                    }
                });
                rb3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int[] selectedIndices = jList.getSelectedIndices();

                        selectedMusicIndices.clear();
                        for (int index : selectedIndices) {
                            selectedMusicIndices.add(index);
                        }

                        for (int index : selectedIndices) {
                            Music selectedMusic = listModel.getElementAt(index);
                            System.out.println("Selected Music: " + selectedMusic.getInfo(1));
                            if (selectedIndices.length == 0) {
                                return;
                            }
                        }
                        topPanel.setVisible(false);
                        remove(topPanel);
                        buttonPanel.setVisible(false);
                        remove(buttonPanel);
                        testList.setVisible(false);
                        remove(testList);


                        JPanel topPanel2 = new JPanel();
                        topPanel2.setBackground(Palette.backgroundWhite);
                        topPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));

                        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        JText text1 = new JText("플레이리스트를 선택하세요", 700, 50);
                        leftPanel.add(text1);

                        leftPanel.setBackground(Palette.backgroundWhite);
                        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

                        topPanel2.add(leftPanel, BorderLayout.NORTH);


                        JPanel buttonPanel2 = new JPanel();
                        buttonPanel2.setBackground(Palette.backgroundWhite);
                        buttonPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));

                        JRoundedButton rb1 = new JRoundedButton("추가", Palette.backgroundWhite, Palette.primaryBlue);
                        buttonPanel2.add(rb1);


                        JRoundedButton backButton = new JRoundedButton("뒤로가기");
                        buttonPanel2.add(backButton);

                        buttonPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 200));

                        add(topPanel2);
                        add(buttonPanel2);

                        JPanel testList2 = new JPanel();
                        testList2.setBackground(Palette.backgroundWhite);
                        testList2.setLayout(new FlowLayout(FlowLayout.LEFT));
                        testList2.setLayout(new BorderLayout());

                        JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        JText text2= new JText("플레이리스트가 없습니다", Palette.primaryBlue,400, 50);
                        emptyPanel.add(text2);
                        emptyPanel.setPreferredSize(new Dimension(1500, 750));
                        emptyPanel.setBackground(Palette.backgroundWhite);

                        DefaultListModel<Playlist> listModel3 = new DefaultListModel<>();
                        for (Playlist list : PlaylistMgr.getInstance().mList) {
                            if (list.getUiTexts()[1].equals(GUIMain.currentUser.getUiTexts()[1])) {
                                listModel3.addElement(list);
                            }
                        }

                        JList<Playlist> jList3 = new JList<>(listModel3);
                        jList3.setCellRenderer(new ItemRenderer<Playlist>());
                        jList3.setBorder(BorderFactory.createEmptyBorder());
                        jList3.setBackground(Palette.backgroundWhite);

                        JScrollPane scrollPane3 = new JScrollPane(jList3);
                        scrollPane3.setBackground(Palette.backgroundWhite);
                        scrollPane3.setBorder(BorderFactory.createEmptyBorder());
                        scrollPane.setPreferredSize(new Dimension(1500,750));

                        testList.setLayout(new FlowLayout(FlowLayout.LEFT));
                        if (listModel3.isEmpty()) {
                            testList2.add(emptyPanel, BorderLayout.CENTER);
                        } else {
                            testList2.add(scrollPane3, BorderLayout.CENTER);
                        }

                        add(testList2);

                        rb1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                int selectedPlaylistIndex = jList3.getSelectedIndex();
                                Playlist selectedPlaylist = listModel3.getElementAt(selectedPlaylistIndex);

                                for (int index : selectedIndices) {
                                    Music selectedMusic = listModel.getElementAt(index);
                                    selectedPlaylist.add(selectedMusic.getUiTexts()[0]);
                                }

                                backButton.doClick();

                            }
                        });

                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 저장해 둔 기존 패널을 다시 보이게 하기
                                topPanel.setVisible(true);
                                add(topPanel);
                                buttonPanel.setVisible(true);
                                add(buttonPanel);
                                testList.setVisible(true);
                                add(testList);

                                topPanel2.setVisible(false);
                                remove(topPanel2);
                                buttonPanel2.setVisible(false);
                                remove(buttonPanel2);
                                testList2.setVisible(false);
                                remove(testList2);


                                revalidate();
                                repaint();
                            }
                        });

                    }
                });
                break;
            case 2:

                JPanel topPanel2 = new JPanel(new BorderLayout());
                topPanel2.setBackground(Palette.backgroundWhite);

                JPanel leftPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JText text2 = new JText("앨범목록", 700, 50);
                leftPanel2.add(text2);

                leftPanel2.setBackground(Palette.backgroundWhite);
                leftPanel2.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

                topPanel2.add(leftPanel2, BorderLayout.NORTH);

                add(topPanel2);
                JPanel testList2 = new JPanel();
                testList2.setBackground(Palette.backgroundWhite);

                DefaultListModel<Album> listModel2 = new DefaultListModel<>();
                for (Album album : AlbumMgr.getInstance().mList) {
                    listModel2.addElement(album);
                }

                JList<Album> jList2 = new JList<>(listModel2);
                jList2.setCellRenderer(new ItemRenderer<Album>());
                jList2.setBorder(BorderFactory.createEmptyBorder());
                jList2.setBackground(Palette.backgroundWhite);

                JScrollPane scrollPane2 = new JScrollPane(jList2);
                scrollPane2.setBackground(Palette.backgroundWhite);
                scrollPane2.setBorder(BorderFactory.createEmptyBorder());
                scrollPane2.setPreferredSize(new Dimension(1500,850));

                testList2.setLayout(new FlowLayout(FlowLayout.LEFT));

                testList2.add(scrollPane2, BorderLayout.CENTER);
                add(testList2);

                jList2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            int selectedIndex = jList2.getSelectedIndex();
                            if (selectedIndex != -1) {
                                Album selectedAlbum = listModel2.getElementAt(selectedIndex);
                                GUIMain.manageList(selectedAlbum.getMusicList());
                            }
                        }
                    }
                });
                break;
            case 3:
                JPanel topPanel3 = new JPanel(new BorderLayout());
                topPanel3.setBackground(Palette.backgroundWhite);

                JPanel leftPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JText text3 = new JText("내 플레이리스트", Palette.primaryBlue,400, 50);
                leftPanel3.add(text3);

                leftPanel3.setBackground(Palette.backgroundWhite);
                leftPanel3.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

                topPanel3.add(leftPanel3, BorderLayout.NORTH);

                add(topPanel3);

                JPanel buttonPanel2 = new JPanel();
                buttonPanel2.setBackground(Palette.backgroundWhite);
                buttonPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
                JTextField tfName = new JTextField(18);
                tfName.setBackground(Palette.primaryGrayAlt);
                tfName.setFont(new Font("LINE Seed Sans KR Regular", Font.PLAIN, 20));
                tfName.setBorder(BorderFactory.createEmptyBorder());
                tfName.setPreferredSize(new Dimension(tfName.getPreferredSize().width, 50));



                JRoundedButton rb4 = new JRoundedButton("새 플레이리스트 추가", Palette.backgroundWhite, Palette.primaryBlue);

                tfName.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            rb4.doClick(); // Simulate a click on the "Add Playlist" button
                        }
                    }
                });
                buttonPanel2.add(tfName);
                buttonPanel2.add(rb4);

                add(topPanel3);
                add(buttonPanel2);

                JPanel testList3 = new JPanel();
                testList3.setBackground(Palette.backgroundWhite);

                DefaultListModel<Playlist> listModel3 = new DefaultListModel<>();
                for (Playlist list : PlaylistMgr.getInstance().mList) {
                    if (list.getUiTexts()[1].equals(GUIMain.currentUser.getUiTexts()[1])) {
                        listModel3.addElement(list);
                    }
                }

                JList<Playlist> jList3 = new JList<>(listModel3);
                jList3.setCellRenderer(new ItemRenderer<Playlist>());
                jList3.setBorder(BorderFactory.createEmptyBorder());
                jList3.setBackground(Palette.backgroundWhite);

                JScrollPane scrollPane3 = new JScrollPane(jList3);
                scrollPane3.setBackground(Palette.backgroundWhite);
                scrollPane3.setBorder(BorderFactory.createEmptyBorder());
                scrollPane3.setPreferredSize(new Dimension(1500,750));

                testList3.setLayout(new FlowLayout(FlowLayout.LEFT));

                testList3.add(scrollPane3, BorderLayout.CENTER);
                add(testList3);

                jList3.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            int selectedIndex = jList3.getSelectedIndex();
                            if (selectedIndex != -1) {
                                Playlist selectedList = listModel3.getElementAt(selectedIndex);
                                selectedList.print();
                                PlayerPanel playerPanel = new PlayerPanel(selectedList.getList());
                                GUIMain.manageList(selectedList.getList());
                            }
                        }
                    }
                });

                rb4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String playlistName = tfName.getText();
                        Playlist newPlaylist = new Playlist();
                        newPlaylist.create(GUIMain.currentUser.getUiTexts()[1], playlistName);

                        JOptionPane.showMessageDialog(
                                ListPanel.this,  // Replace YourParentClass with the actual class where this code resides
                                "Playlist '" + playlistName + "'가 생성되었습니다!",
                                "Playlist Creation",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        // Update playlist after creation
                        updatePlaylist(testList3);
                    }
                });
                break;
            default:
        }
    }
    private void updateList(JPanel testList) {

        DefaultListModel<Music> listModel = new DefaultListModel<>();
        for (Music music : MusicMgr.getInstance().mList) {
            listModel.addElement(music);
        }

        JList<Music> jList = new JList<>(listModel);
        jList.setCellRenderer(new ItemRenderer<Music>());
        jList.setBorder(BorderFactory.createEmptyBorder());
        jList.setBackground(Palette.backgroundWhite);

        JScrollPane scrollPane = new JScrollPane(jList);
        scrollPane.setBackground(Palette.backgroundWhite);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(1600,750));

        testList.setLayout(new FlowLayout(FlowLayout.LEFT));

        testList.removeAll();

        testList.add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void updatePlaylist(JPanel testList) {
        DefaultListModel<Playlist> listModel3 = new DefaultListModel<>();
        for (Playlist list : PlaylistMgr.getInstance().mList) {
            if (list.getUiTexts()[1].equals(GUIMain.currentUser.getUiTexts()[1])) {
                listModel3.addElement(list);
            }
        }

        JList<Playlist> jList3 = new JList<>(listModel3);
        jList3.setCellRenderer(new ItemRenderer<Playlist>());
        jList3.setBorder(BorderFactory.createEmptyBorder());
        jList3.setBackground(Palette.backgroundWhite);

        JScrollPane scrollPane3 = new JScrollPane(jList3);
        scrollPane3.setBackground(Palette.backgroundWhite);
        scrollPane3.setBorder(BorderFactory.createEmptyBorder());
        scrollPane3.setPreferredSize(new Dimension(1600,750));

        testList.setLayout(new FlowLayout(FlowLayout.LEFT));

        testList.removeAll();
        testList.add(scrollPane3, BorderLayout.CENTER);

        jList3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = jList3.getSelectedIndex();
                    if (selectedIndex != -1) {
                        Playlist selectedList = listModel3.getElementAt(selectedIndex);
                        selectedList.print();
                        PlayerPanel playerPanel = new PlayerPanel(selectedList.getList());
                        GUIMain.manageList(selectedList.getList());
                    }
                }
            }
        });

        revalidate();
        repaint();
    }

}
