package mainGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import admin_gui.TableFormat;

@SuppressWarnings("serial")
public class MainTopPanel extends JPanel {
	// Item 검색 탭의 상단 패널 구성하기
	JTextField kwdTextField = new JTextField("", 20);
	int tabNumber = 0; // 0(음악), 1(앨범), 2(사용자)

	public void setupTopPane(MainTableFormat tf, int num) {
		tabNumber = num;
		setupTopPane(tf);
	}

	void setupTopPane(MainTableFormat tf) {
		JPanel topPane = new JPanel();
		JButton detail = new JButton("상세정보");
		if (tabNumber == 1) {
			topPane.add(detail, BorderLayout.LINE_START);
		}

		topPane.add(kwdTextField, BorderLayout.CENTER);
		JButton search = new JButton("검색");
		topPane.add(search, BorderLayout.LINE_END);
		add(topPane, BorderLayout.PAGE_START);

        detail.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (e.getActionCommand().equals("상세정보")) {
        			//상세정보를 누르면 수록곡을 음악 탭에다 표시
        			int selectedIndex = tf.table.getSelectedRow();
        			String albumName = (String)tf.tableModel.getValueAt(selectedIndex, 0); //앨범명
        			MainFrame.getInstance().searchMusicByAlbum(albumName);
            	}
           }
        });
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("검색")) {
					tf.loadData(kwdTextField.getText());
				}
			}
		});

		try {
			Font font = new Font("나눔바른고딕", Font.PLAIN, 15);
			// detail.setFont(font);
			search.setFont(font);
		} catch (Exception ignore) {
		}
	}
}
