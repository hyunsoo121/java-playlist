package admin_gui;

import javax.swing.*;
import java.awt.*;

public class DetailDialog extends JDialog {
	String[][] itemLabel = {
			{ "ID", "제목", "아티스트", "앨범명" },
			{ "아티스트", "데뷔일", "타입", "소속사" },
			{ "ID", "전화번호", "이름", "보유한 플레이리스트 수" }
	};
	String[] itemDetails;
	JLabel details[] = new JLabel[5];
	int tabNumber = 0;

	DetailDialog(String[] texts) {
		super(AdminGUI.mainFrame);
		itemDetails = texts;
	}

	void setup(int num, TableFormat tf) { // 탭 정보를 불러와 라벨 텍스트를 재설정합니다.
		tabNumber = num;
		setup(tf);
	}

	void setup(TableFormat tf) {
		setLocationRelativeTo(null);
		setTitle("상세정보");
		JPanel pane = new JPanel(new BorderLayout());
		JPanel lpane = new JPanel(new GridLayout(4, 1));
		// JLabel photo = new JLabel(" Photo ");
		// photo.setOpaque(true);
		// photo.setPreferredSize(new Dimension(282, 282));
		// photo.setBackground(Color.YELLOW);

		// for(Object o : tf.dataMgr.search(itemDetails[1])) {
		// System.out.println(o.getClass());
		// }

		// System.out.println(tabNumber);
		// System.out.println(tf.dataMgr.search(itemDetails[1]).get(0).getImagePath());
		// tf.dataMgr.search(itemDetails[1]).get(0).getImagePath();

		ImageIcon imgIcon = null;
		try {
			// tabN 0:음악, 1:아티스트, 2:사용자
			if (tabNumber == 0) {
				imgIcon = new ImageIcon("res/" + tf.dataMgr.search(itemDetails[1]).get(0).getImagePath());
			}
			if (tabNumber == 1) {
				imgIcon = new ImageIcon("res/" + tf.dataMgr.search(itemDetails[0]).get(0).getImagePath());
			}
			if (tabNumber == 2) {
				imgIcon = new ImageIcon("res/001.jpg");
			}
		} catch (Exception e) {
			if (tabNumber != 2) {
				imgIcon = new ImageIcon("res/002.jpg");
			}
			imgIcon = new ImageIcon("res/001.jpg");
		}

		// imagePath
		// imgIcon = new ImageIcon("res/" +
		// tf.dataMgr.search(itemDetails[1]).get(0).getImagePath());
		Image img = imgIcon.getImage();
		Image updateImg = img.getScaledInstance(282, 282, Image.SCALE_SMOOTH);
		ImageIcon updateIcon = new ImageIcon(updateImg);
		JLabel photo = new JLabel(updateIcon);
		photo.setIcon(updateIcon);

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
