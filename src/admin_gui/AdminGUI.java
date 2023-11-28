package admin_gui;

import mgr.*;
import stream.Stream;

import javax.swing.*;
import java.awt.*;

public class AdminGUI {
    public static void main(String[] args) {
        System.out.format("[AdminGUI] 콘솔 종료 시, GUI 메뉴에 진입합니다.\n");
        System.out.format("==========================================\n");
        Stream.getInstance().run();
        startGUI();
    }

    private static AdminGUI main = null;

    public static AdminGUI getInstance() {
        if (main == null)
            main = new AdminGUI();
        return main;
    }

    private static void startGUI() {
        SwingUtilities.invokeLater(() -> AdminGUI.getInstance().createAndShowGUI());
    }

    static JFrame mainFrame = new JFrame("Colon: Admin GUI");

    private void createAndShowGUI() {
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane jtab = new JTabbedPane();

        setupMusicPane(); // 이곳에 메서드를 삽입하여 Pane 을 추가할 수 있습니다.
        setupAlbumPane();
        setupArtistPane();
        setupUserPane();
        setupPlaylistPane();
        jtab.add("음악", musicPane);
        jtab.add("앨범", albumPane);
        jtab.add("아티스트", artistPane);
        jtab.add("사용자", userPane);
        jtab.add("플레이리스트", playlistPane);

        mainFrame.getContentPane().add(jtab);
        mainFrame.pack();
        mainFrame.setVisible(true);

        // 폰트 변경
        try {
            Font font = new Font("나눔바른고딕", Font.PLAIN, 15);
            jtab.setFont(font);
        } catch (Exception ignore) {
        }
    }

    // 음악 탭

    private JPanel musicPane;
    TableFormat musicTable = new TableFormat();
    TopPanel musicTop = new TopPanel();

    private void setupMusicPane() {
        musicPane = new JPanel(new BorderLayout());
        musicTable.tableTitle = "music";
        musicTable.addComponentsToPane(MusicMgr.getInstance());
        musicTop.setupTopPane(musicTable, 0);
        musicPane.add(musicTop, BorderLayout.NORTH);
        musicPane.add(musicTable, BorderLayout.CENTER);
    }

    // 앨범 탭

    private JPanel albumPane;
    TableFormat albumTable = new TableFormat();
    SubTableFormat albumMusicTable = new SubTableFormat();

    private void setupAlbumPane() {
        albumPane = new JPanel(new BorderLayout());
        albumTable.tableTitle = "album";
        albumTable.addComponentsToPane(AlbumMgr.getInstance());
        albumPane.add(albumTable, BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        albumMusicTable.tableTitle = "album-music";
        albumMusicTable.addComponentsToPane(MusicMgr.getInstance());
        bottom.add(new JLabel("앨범 수록곡"), BorderLayout.LINE_START);
        bottom.add(albumMusicTable, BorderLayout.CENTER);
        albumPane.add(bottom, BorderLayout.SOUTH);
    }

    // 아티스트 탭

    private JPanel artistPane;
    TableFormat artistTable = new TableFormat();
    TopPanel artistTop = new TopPanel();

    private void setupArtistPane() {
        artistPane = new JPanel(new BorderLayout());
        artistTable.tableTitle = "artist";
        artistTable.addComponentsToPane(ArtistMgr.getInstance());
        artistTop.setupTopPane(artistTable, 1);
        artistPane.add(artistTop, BorderLayout.NORTH);
        artistPane.add(artistTable, BorderLayout.CENTER);
    }

    // 사용자 탭

    private JPanel userPane;
    TableFormat userTable = new TableFormat();
    TopPanel userTop = new TopPanel();

    private void setupUserPane() {
        userPane = new JPanel(new BorderLayout());
        userTable.tableTitle = "user";
        userTable.addComponentsToPane(UserMgr.getInstance());
        userTop.setupTopPane(userTable, 2);
        userPane.add(userTop, BorderLayout.NORTH);
        userPane.add(userTable, BorderLayout.CENTER);
    }

    // 플레이리스트 탭

    private JPanel playlistPane;
    TableFormat playlistTable = new TableFormat();
    SubTableFormat playlistMusicTable = new SubTableFormat();

    private void setupPlaylistPane() {
        playlistPane = new JPanel(new BorderLayout());
        playlistTable.tableTitle = "playlist";
        playlistTable.addComponentsToPane(PlaylistMgr.getInstance());
        playlistPane.add(playlistTable, BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        playlistMusicTable.tableTitle = "playlist-music";
        playlistMusicTable.addComponentsToPane(MusicMgr.getInstance());
        bottom.add(new JLabel("플레이리스트"), BorderLayout.LINE_START);
        bottom.add(playlistMusicTable, BorderLayout.CENTER);
        playlistPane.add(bottom, BorderLayout.SOUTH);
    }

    /*
     * [1단 테이블]
     * - 상단 검색창, 상세보기를 포함한 기본 테이블 탭 메서드
     * 
     * private JPanel musicPane;
     * TableFormat musicTable = new TableFormat();
     * TopPanel musicTop = new TopPanel();
     * private void setupMusicPane() {
     * musicPane = new JPanel(new BorderLayout());
     * musicTable.tableTitle = "music";
     * musicTable.addComponentsToPane(MusicMgr.getInstance());
     * musicTop.setupTopPane(musicTable);
     * musicPane.add(musicTop, BorderLayout.NORTH);
     * musicPane.add(musicTable, BorderLayout.CENTER);
     * }
     */

    /*
     * [2단 테이블]
     * - 앨범, 플레이리스트에 사용된 2단 테이블 탭 메서드
     * 
     * private JPanel albumPane;
     * TableFormat albumTable = new TableFormat();
     * SubTableFormat albumMusicTable = new SubTableFormat();
     * private void setupAlbumPane() {
     * albumPane = new JPanel(new BorderLayout());
     * albumTable.tableTitle = "album";
     * albumTable.addComponentsToPane(AlbumMgr.getInstance());
     * albumPane.add(albumTable, BorderLayout.CENTER);
     * JPanel bottom = new JPanel();
     * albumMusicTable.tableTitle = "album-music";
     * albumMusicTable.addComponentsToPane(MusicMgr.getInstance());
     * bottom.add(new JLabel("앨범 수록곡"), BorderLayout.LINE_START);
     * bottom.add(albumMusicTable, BorderLayout.CENTER);
     * albumPane.add(bottom, BorderLayout.SOUTH);
     * }
     */
}