package mainGUI;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import mgr.PlaylistMgr;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MainPlaylistFrame extends JFrame {
	
	private static MainPlaylistFrame playlistFrame = null;
	public static MainPlaylistFrame getInstance() {
		if(playlistFrame == null)
			playlistFrame = new MainPlaylistFrame();
		return playlistFrame;
	}
	
	void setContents() {
		setTitle("playlist");
		setSize(500,400);
		
		Container mainPane = getContentPane();
		mainPane.setSize(this.getSize());
		mainPane.setLayout(new GridBagLayout());
		GridBagConstraints[] gridBag = new GridBagConstraints[2];
		
		setTopPane();
		setListTable();
		gridBag[0] = new GridBagConstraints();
		gridBag[0].gridx = 0;
		gridBag[0].gridy = 0;
		gridBag[0].weightx = 1;
		gridBag[0].weighty = 1;
		gridBag[0].fill = GridBagConstraints.HORIZONTAL;
		mainPane.add(topPane, gridBag[0]);
		gridBag[1] = new GridBagConstraints();
		gridBag[1].gridx = 0;
		gridBag[1].gridy = 1;
		gridBag[1].weightx = 1;
		gridBag[1].weighty = 10;
		gridBag[1].fill = GridBagConstraints.BOTH;
		mainPane.add(listTable, gridBag[1]);
		
		setContentPane(mainPane);
	}
	
	JPanel topPane;
	JTextField text;
	void setTopPane() {
		topPane = new JPanel();
		text = new JTextField("새로운 재생목록", 20);
		JButton load = new JButton("로드");
		JButton add = new JButton("추가");
		JButton del = new JButton("삭제");
		
		topPane.setLayout(new FlowLayout());
		topPane.add(load);
		topPane.add(text);
		topPane.add(add);
		topPane.add(del);
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("추가")) {
					//플레이리스트 추가
					if(text.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "재생목록 이름을 입력하세요");
					}
				}
			}
		});
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("삭제")) {
					//플레이리스트 삭제
				}
			}
		});
	}
	
	MainTableFormat listTable;
	void setListTable() {
		listTable = new MainTableFormat();
		listTable.tableTitle = "playlist";
		listTable.addComponentsToPane(PlaylistMgr.getInstance()); //계정 연동 필요
	}
	
	void loadFrame() {
		setVisible(true);
	}
	
}
