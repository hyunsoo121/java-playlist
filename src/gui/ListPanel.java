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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ListPanel extends JPanel {
    private ArrayList<Integer> selectedMusicIndices = new ArrayList<>();
    public ListPanel(int page){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Palette.backgroundWhite);

        switch (page) {
            case 1:
                JPanel topPanel = new JPanel();
                topPanel.setBackground(Palette.backgroundWhite);
                topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                JText text1 = new JText("차트", 700, 50);
                topPanel.add(text1, BorderLayout.WEST);
                topPanel.add(Box.createVerticalGlue());

                JPanel buttonPanel = new JPanel();
                buttonPanel.setBackground(Palette.backgroundWhite);
                buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                JRoundedButton rb1 = new JRoundedButton("인기순", Palette.backgroundWhite, Palette.primaryBlue);
                buttonPanel.add(rb1);

                JRoundedButton rb2 = new JRoundedButton("최신순");
                buttonPanel.add(rb2);

                JRoundedButton rb3 = new JRoundedButton("플레이리스트에 추가");
                buttonPanel.add(rb3, BorderLayout.SOUTH);

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
                scrollPane.setPreferredSize(new Dimension(900,500));

                testList.add(scrollPane, BorderLayout.CENTER);
                add(testList);

                rb1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Stream.sortList(1);
                        updateList(testList);
                        rb2.setBackground(Palette.backgroundWhite);
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
                        rb1.setBackground(Palette.backgroundWhite);
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

                        JText text1 = new JText("플레이리스트를 선택하세요", 700, 50);
                        topPanel2.add(text1, BorderLayout.WEST);
                        topPanel2.add(Box.createVerticalGlue());

                        JRoundedButton rb1 = new JRoundedButton("추가", Palette.backgroundWhite, Palette.primaryBlue);
                        topPanel2.add(rb1);
                        JRoundedButton backButton = new JRoundedButton("뒤로가기", Palette.backgroundWhite, Palette.primaryBlue);
                        topPanel2.add(backButton);

                        add(topPanel2);

                        JPanel testList2 = new JPanel();
                        testList2.setBackground(Palette.backgroundWhite);

                        DefaultListModel<Playlist> listModel3 = new DefaultListModel<>();
                        for (Playlist list : PlaylistMgr.getInstance().mList) {
                            listModel3.addElement(list);
                        }

                        JList<Playlist> jList3 = new JList<>(listModel3);
                        jList3.setCellRenderer(new ItemRenderer<Playlist>());
                        jList3.setBorder(BorderFactory.createEmptyBorder());
                        jList3.setBackground(Palette.backgroundWhite);

                        JScrollPane scrollPane3 = new JScrollPane(jList3);
                        scrollPane3.setBackground(Palette.backgroundWhite);
                        scrollPane3.setBorder(BorderFactory.createEmptyBorder());
                        scrollPane3.setPreferredSize(new Dimension(900,500));

                        testList2.add(scrollPane3, BorderLayout.CENTER);
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
                JPanel topPanel2 = new JPanel();
                topPanel2.setBackground(Palette.backgroundWhite);
                topPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));

                JText text3 = new JText("앨범", 700, 50);
                topPanel2.add(text3);

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
                scrollPane2.setPreferredSize(new Dimension(900,500));

                testList2.add(scrollPane2, BorderLayout.CENTER);
                add(testList2);

                jList2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            int selectedIndex = jList2.getSelectedIndex();
                            if (selectedIndex != -1) {
                                Album selectedAlbum = listModel2.getElementAt(selectedIndex);
                                PlayerPanel playerPanel = new PlayerPanel(selectedAlbum.getMusicList());
                                //PlayerPanel show
                            }
                        }
                    }
                });
                break;
            case 3:
                JPanel topPanel3 = new JPanel();
                topPanel3.setBackground(Palette.backgroundWhite);
                topPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));

                JText text4 = new JText("플레이리스트", Palette.primaryBlue,400, 40);
                topPanel3.add(text4);

                add(topPanel3);

                JPanel testList3 = new JPanel();
                testList3.setBackground(Palette.backgroundWhite);

                DefaultListModel<Playlist> listModel3 = new DefaultListModel<>();
                for (Playlist list : PlaylistMgr.getInstance().mList) {
                    listModel3.addElement(list);
                }

                JList<Playlist> jList3 = new JList<>(listModel3);
                jList3.setCellRenderer(new ItemRenderer<Playlist>());
                jList3.setBorder(BorderFactory.createEmptyBorder());
                jList3.setBackground(Palette.backgroundWhite);

                JScrollPane scrollPane3 = new JScrollPane(jList3);
                scrollPane3.setBackground(Palette.backgroundWhite);
                scrollPane3.setBorder(BorderFactory.createEmptyBorder());
                scrollPane3.setPreferredSize(new Dimension(900,500));

                testList3.add(scrollPane3, BorderLayout.CENTER);
                add(testList3);

                jList3.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            int selectedIndex = jList3.getSelectedIndex();
                            if (selectedIndex != -1) {
                                Playlist selectedList = listModel3.getElementAt(selectedIndex);
                                PlayerPanel playerPanel = new PlayerPanel(selectedList.getList());
                                //PlayerPanel show
                            }
                        }
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
        scrollPane.setPreferredSize(new Dimension(900, 500));

        testList.removeAll();

        testList.add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
