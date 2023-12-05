package gui;

import mgr.UserMgr;
import stream.User;
import ui_config.JRoundedButton;
import ui_config.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    static Dimension homeSize = new Dimension(1200, 800);
    public HomePanel(){     // 이후 삭제
        this(UserMgr.getInstance().find("010-1111-1111"));
    }
    public HomePanel(User currentUser) {
        setSize(homeSize);
        setLayout(new BorderLayout());
        setBackground(Palette.backgroundWhite);

        JPanel sidePane = new JPanel(new BorderLayout());
        sidePane.setBackground(Palette.backgroundWhiteAlt);
        sidePane.setPreferredSize(new Dimension(250, 700));

        sidePane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Palette.primaryGray));

        ImageIcon imageIcon = new ImageIcon("res/000.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(110, 44, Image.SCALE_SMOOTH);

        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel logo = new JLabel(scaledImageIcon);

        JRoundedButton sideButton1 = new JRoundedButton("콜론차트", 400, 17);
        JRoundedButton sideButton2 = new JRoundedButton("앨범목록", 400, 17);
        JRoundedButton sideButton3 = new JRoundedButton("내 플레이리스트", 400, 17);
        JRoundedButton sideButton4 = new JRoundedButton( currentUser.getUiTexts()[2] + "님 로그아웃", 400, 17);

        Dimension buttonSize = new Dimension(250, 60);

        sideButton1.setMaximumSize(buttonSize);
        sideButton2.setMaximumSize(buttonSize);
        sideButton3.setMaximumSize(buttonSize);


        JPanel sideTopPane = new JPanel();
        sideTopPane.setLayout(new BoxLayout(sideTopPane, BoxLayout.Y_AXIS));
        sideTopPane.add(logo);
        sideTopPane.add(sideButton1);
        sideTopPane.add(sideButton2);
        sideTopPane.add(sideButton3);

        JPanel sideBottomPane = new JPanel();
        sideBottomPane.setLayout(new BoxLayout(sideBottomPane, BoxLayout.Y_AXIS));
        sideBottomPane.add(sideButton4);

        sidePane.add(sideTopPane, BorderLayout.NORTH);
        sidePane.add(sideBottomPane, BorderLayout.SOUTH);

        add(sidePane, BorderLayout.WEST);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.setBackground(Palette.backgroundWhite);

        // 여기에 간격을 조절하는 패널 추가
        contentPane.add(Box.createRigidArea(new Dimension(100, 0))); // 간격 조절

        JPanel mainPane = new JPanel(new CardLayout());
        CardLayout cardLayout = (CardLayout) mainPane.getLayout();

        mainPane.setBackground(Palette.backgroundWhite);
        mainPane.setPreferredSize(new Dimension(900, 700));

        ListPanel listPanel1 = new ListPanel(1);
        ListPanel listPanel2 = new ListPanel(2);
        ListPanel listPanel3 = new ListPanel(3);

        mainPane.add(listPanel1, "tab1");
        mainPane.add(listPanel2, "tab2");
        mainPane.add(listPanel3, "tab3");

        sideButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPane, "tab1");
            }
        });
        sideButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPane, "tab2");
            }
        });
        sideButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPane, "tab3");
            }
        });
        sideButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIMain.logout();
            }
        });

        contentPane.add(mainPane);

        add(contentPane, BorderLayout.CENTER);
    }
}
