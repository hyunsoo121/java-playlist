package mainGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import admin_gui.SubTableFormat;
import admin_gui.TableFormat;
import admin_gui.TopPanel;
import mgr.AlbumMgr;
import mgr.MusicMgr;
import mgr.PlaylistMgr;
import stream.Stream;

public class MainFrame extends JFrame {
	
	public static void main(String[] args) {
		Stream.getInstance().run();
		startGUI();
	}
	
	private static void startGUI() {
		SwingUtilities.invokeLater(() -> MainFrame.getInstance().guiMain());
	}
	
	private static MainFrame mainFrame = null;
	public static MainFrame getInstance() {
		if(mainFrame == null)
			mainFrame = new MainFrame();
		return mainFrame;
	}
	
	Container mainPane;
	private void guiMain() {
		setTitle("Test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 650);
		
		mainPane = getContentPane();
		mainPane.setSize(this.getSize());
		GridBagConstraints[] gridBag = new GridBagConstraints[2];
		mainPane.setLayout(new GridBagLayout());
		//getContentPane().setBackground(null);
		
		setMusicPane();
		setPlaylistPane();
		setInfoPane();
		setRightPane();
		setupAlbumPane();
		
		JTabbedPane jtab = new JTabbedPane();
		jtab.add("음악", musicPane);
		jtab.add("앨범", albumPane);
		gridBag[0] = new GridBagConstraints();
		gridBag[0].gridx = 0;
		gridBag[0].gridy = 0;
		gridBag[0].weightx = 1;
		gridBag[0].weighty = 1;
		gridBag[0].fill = GridBagConstraints.BOTH;
		mainPane.add(jtab, gridBag[0]);
		
		gridBag[1] = new GridBagConstraints();
		gridBag[1].gridx = 1;
		gridBag[1].gridy = 0;
		gridBag[1].weightx = 2;
		gridBag[1].weighty = 1;
		gridBag[1].fill = GridBagConstraints.BOTH;
		mainPane.add(rightPane, gridBag[1]);
		
		setContentPane(mainPane);
		setVisible(true);
	}
	
    private JPanel albumPane;
    MainTableFormat albumTable;
    MainTopPanel albumTop;
    private void setupAlbumPane() {
    	albumTable = new MainTableFormat();
        albumPane = new JPanel(new BorderLayout());
        MainTopPanel albumTop = new MainTopPanel();
        albumTable.tableTitle = "album";
        albumTop.setupTopPane(albumTable, 1);
        albumPane.add(albumTop, BorderLayout.NORTH);
        albumTable.addComponentsToPane(AlbumMgr.getInstance());
        albumPane.add(albumTable, BorderLayout.CENTER);
    }
	
	private JPanel musicPane;
	MainTableFormat musicTable;
	MainTopPanel musicTop;
	private void setMusicPane() {
		musicPane = new JPanel(new BorderLayout());
		musicTable = new MainTableFormat();
		musicTop = new MainTopPanel();
		musicTable.tableTitle = "music";
        musicTable.addComponentsToPane(MusicMgr.getInstance());
        musicTop.setupTopPane(musicTable, 0);
        musicPane.add(musicTop, BorderLayout.NORTH);
        musicPane.add(musicTable, BorderLayout.CENTER);
	}
	
//	private JPanel playlistPane;
//	private void setPlaylistPane() {
//		playlistPane = new JPanel();
//		playlistPane.setBackground(new Color(10,10,155));
//	}
	
	MainPlaylistPanel playlistPane;
	private void setPlaylistPane() {
		playlistPane = new MainPlaylistPanel();
		playlistPane.initPlaylistPane();
	}
	public static void addPlaylistEvent() {
		
	}
	
	private JPanel infoPane;
	private JPanel innerInfoPane;
	private JPanel imgPane;
	JLabel infoLabel[] = new JLabel[4];
	JLabel photo;
	ImageIcon updateIcon;
	private void setInfoPane() {
		//infoPane = new JPanel(new GridLayout(1, 2));
//		FlowLayout infoFlow = new FlowLayout();
//		infoFlow.setHgap(0);
//		infoFlow.setHgap(0);
		infoPane = new JPanel(new BorderLayout());
		
		imgPane = new JPanel();
		GridLayout innerGrid = new GridLayout(4, 1);
		innerGrid.setHgap(0);
		innerGrid.setVgap(0);
		innerInfoPane = new JPanel(innerGrid);
		innerInfoPane.setBackground(new Color(0,200,0));
		infoLabel[0] = new JLabel(" ID: ");
		infoLabel[1] = new JLabel(" 제목: ");
		infoLabel[2] = new JLabel(" 아티스트: ");
		infoLabel[3] = new JLabel(" 앨범명: ");
		innerInfoPane.add(infoLabel[0]);
		innerInfoPane.add(infoLabel[1]);
		innerInfoPane.add(infoLabel[2]);
		innerInfoPane.add(infoLabel[3]);
		
		imgPane = new JPanel();
		ImageIcon imgIcon = new ImageIcon("res/001.jpg");
		Image img = imgIcon.getImage();
		Image updateImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		updateIcon = new ImageIcon(updateImg);
		photo = new JLabel(updateIcon);
		imgPane.add(photo);
		
		infoPane.add(innerInfoPane, BorderLayout.CENTER);
		infoPane.add(photo, BorderLayout.EAST);
		//infoPane.add(imgPane);
	}
	void infoRefresh(String title, String[] rowValue) {
		int tabNum = -1;
		if(title.contentEquals("music"))
			tabNum = 0;
		else tabNum = 1;
		String[][] itemLabel = {
				{" ID"," 제목"," 아티스트"," 앨범명"},
				{" 아티스트"," 데뷔일"," 타입"," 소속사"},
		};
		
		//infoLabel[0].setText(String.format(" %s: %50s", itemLabel[tabNum][0], rowValue[0]));
		infoLabel[0].setText(itemLabel[tabNum][0] + ": " + rowValue[0]);
		infoLabel[1].setText(itemLabel[tabNum][1] + ": " + rowValue[1]);
		infoLabel[2].setText(itemLabel[tabNum][2] + ": " + rowValue[2]);
		infoLabel[3].setText(itemLabel[tabNum][3] + ": " + rowValue[3]);
		
		ImageIcon imgIcon = null;
		try{
			//tabN 0:음악, 1:아티스트
			if(tabNum == 0) {
				imgIcon = new ImageIcon("res/" + MusicMgr.getInstance().search(rowValue[0]).get(0).getImagePath());
			}
			if(tabNum == 1) {
				imgIcon = new ImageIcon("res/" + AlbumMgr.getInstance().search(rowValue[0]).get(0).getImagePath());
			}
		}catch(Exception e) {
			imgIcon = new ImageIcon("res/001.jpg");
		}
		Image img = imgIcon.getImage();
		Image updateImg = img.getScaledInstance(200,200, Image.SCALE_SMOOTH);
		updateIcon = new ImageIcon(updateImg);
		photo.setIcon(updateIcon);
	}
	
	private JPanel rightPane;
	private void setRightPane() {
		rightPane = new JPanel();
		GridBagConstraints[] bag = new GridBagConstraints[2];
		rightPane.setLayout(new GridBagLayout());
		bag[0] = new GridBagConstraints();
		bag[0].gridx = 0;
		bag[0].gridy = 0;
		bag[0].weightx = 1;
		bag[0].weighty = 1;
		bag[0].fill = GridBagConstraints.BOTH;
		rightPane.add(infoPane, bag[0]);
		
		bag[1] = new GridBagConstraints();
		bag[1].gridx = 0;
		bag[1].gridy = 1;
		bag[1].weightx = 1;
		bag[1].weighty = 5;
		bag[1].fill = GridBagConstraints.BOTH;
		rightPane.add(playlistPane, bag[1]);
	}

	public void searchMusicByAlbum(String albumName) {
		// TODO Auto-generated method stub
		musicTable.loadData(albumName);
	}
	
}
