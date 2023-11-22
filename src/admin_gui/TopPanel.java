package admin_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {
    // Item 검색 탭의 상단 패널 구성하기
    JTextField kwdTextField = new JTextField("", 20);
    int tabNumber = 0; // 0(음악), 1(아티스트), 2(사용자)

    public void setupTopPane(TableFormat tf, int num) {
        tabNumber = num;
        setupTopPane(tf);
    }

    void setupTopPane(TableFormat tf) {
        JPanel topPane = new JPanel();
        JButton detail = new JButton("상세정보");
        topPane.add(detail, BorderLayout.LINE_START);
        topPane.add(kwdTextField, BorderLayout.CENTER);
        JButton search = new JButton("검색");
        topPane.add(search, BorderLayout.LINE_END);
        add(topPane, BorderLayout.PAGE_START);

        detail.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("상세정보")) {
                    tf.showDetail(tabNumber);
                    // tf.dataMgr.search(TOOL_TIP_TEXT_KEY)
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
            detail.setFont(font);
            search.setFont(font);
        } catch (Exception ignore) {
        }
    }
}
