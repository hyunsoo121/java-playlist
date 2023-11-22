package mainGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import facade.IDataEngine;
import mgr.MusicMgr;

@SuppressWarnings("serial")
public class MainPlaylistPanel extends JPanel {
	JPanel buttonPane;
	JTable playlistTable;
	DefaultTableModel tableModel;
	int selectedIndex = -1; //playlistTable.getSelectedRow();
	MainPlaylistFrame playlistFrame;

	public MainPlaylistPanel() {
		this.setLayout(new BorderLayout());
	}

	void initPlaylistPane() {
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		setButtonPane();
		
		String[] header = MusicMgr.getInstance().getColumnNames();
		tableModel = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		playlistTable = new JTable(tableModel);
		
		playlistFrame = new MainPlaylistFrame();
		playlistFrame.setContents();
		
		ListSelectionModel rowSM = playlistTable.getSelectionModel();
		rowSM.addListSelectionListener(playlistTable);
		playlistTable.setPreferredScrollableViewportSize(new Dimension(300,200));
		playlistTable.setFillsViewportHeight(true);
		playlistTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.add(buttonPane, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(playlistTable);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	void setButtonPane() {
		JButton add = new JButton("추가");
		JButton del = new JButton("삭제");
		JButton manage = new JButton("관리");
		buttonPane.add(add);
		buttonPane.add(del);
		buttonPane.add(manage);
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("추가")) {
					int index = MainFrame.getInstance().musicTable.selectedIndex;
					String[] rowText = new String[MainFrame.getInstance().musicTable.tableModel.getColumnCount()];
					// MainFrame.getInstance().musicTable.tableModel.getColumnCount();
					for(int i = 0; i < rowText.length; i++) {
						rowText[i] = (String)MainFrame.getInstance().musicTable.tableModel.getValueAt(index, i);
					}
					tableModel.addRow(rowText);
				}
			}
		});
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("삭제")) {
					selectedIndex = playlistTable.getSelectedRow();
					if(selectedIndex < 0) {
						JOptionPane.showMessageDialog(null, "선택된 음악이 없습니다.");
						return;
					}
					tableModel.removeRow(playlistTable.getSelectedRow());
				}
			}
		});
		manage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("관리")) {
					playlistFrame.loadFrame();
				}
			}
		});
	}
}
