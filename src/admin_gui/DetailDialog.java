package admin_gui;

import javax.swing.*;
import java.awt.*;

public class DetailDialog extends JDialog {
	String[][] itemLabel = {
			{"ID","제목","아티스트","앨범명"},
			{"아티스트","데뷔일","타입","소속사"},
			{"ID","전화번호","이름","보유한 플레이리스트 수"}
	};
	String[] itemDetails;
	JLabel details[] = new JLabel[5];
	int tabNumber = 0;
	DetailDialog(String[] texts) {
		super(AdminGUI.mainFrame);
		itemDetails = texts;
	}

	void setup(int num) {		// 탭 정보를 불러와 라벨 텍스트를 재설정합니다.
		tabNumber = num;
		setup();
	}

	void setup() {
		setLocationRelativeTo(null);
		setTitle("상세정보");
		JPanel pane = new JPanel(new BorderLayout());
		JPanel lpane = new JPanel(new GridLayout(4, 1));
		JLabel photo = new JLabel("   Photo   ");
		photo.setOpaque(true);
		photo.setPreferredSize(new Dimension(282, 282));
		photo.setBackground(Color.YELLOW);
		details[0] = new JLabel(itemLabel[tabNumber][0] + ": " + itemDetails[0]);
		details[1] = new JLabel(itemLabel[tabNumber][1] + ": " + itemDetails[1]);
		details[2] = new JLabel(itemLabel[tabNumber][2] + ": " + itemDetails[2]);
		details[3] = new JLabel(itemLabel[tabNumber][3] + ": " + itemDetails[3]);
		lpane.add(details[0]);
		lpane.add(details[1]);
		lpane.add(details[2]);
		lpane.add(details[3]);
		pane.add(lpane, BorderLayout.CENTER);
		pane.add(photo, BorderLayout.LINE_END);
		this.setMinimumSize(new Dimension(700, 282));
		setContentPane(pane);
	}
}
