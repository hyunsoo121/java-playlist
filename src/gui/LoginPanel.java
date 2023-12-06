package gui;

import gui.login.User;
import gui.login.UserDataSet;
import mgr.UserMgr;
import ui_config.JRoundedButton;
import ui_config.JText;
import ui_config.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginPanel extends JPanel {
    private UserDataSet users = new UserDataSet();
    private JRoundedButton btnCancel;

    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);
    JPanel pnlJoin;
    JPanel pnlLogin;

    int tfSize = 18;
    Dimension btnSize = new Dimension(100, 25);

    Font font = new Font("LINE Seed Sans KR Regular", Font.PLAIN, 20);
    public LoginPanel(){
        users.readAll("user.txt");
        setDisplay_logo();
        setDisplay_login();
        setDisplay_join();
        cardPanel.setBounds(788, 400, pnlJoin.getPreferredSize().width, pnlJoin.getPreferredSize().height);
        add(cardPanel);
    }

    public void setDisplay_logo(){
        ImageIcon icon = new ImageIcon("./res/000.png");
        Image img = icon.getImage();
        ImageIcon changeIcon = new ImageIcon((img));
        JLabel lbl = new JLabel(changeIcon);
        setLayout(null);
        lbl.setBounds(838, 240, 250, 100);
        add(lbl);

        //설명 추가
        JText lblexplain = new JText("뮤직 플레이어", 700, 30);
        lblexplain.setBounds(870, 310,250,100);
        add(lblexplain);
    }


    //로그인 패널
    public void setDisplay_login() {
        JTextField tfId;
        JTextField tfPhoneNum;
        JRoundedButton btnLogin;
        JRoundedButton btnJoin;

        tfId = new JTextField(tfSize);
        tfId.setFont(font);
        tfId.setBorder(BorderFactory.createEmptyBorder());
        tfId.setPreferredSize(new Dimension(tfId.getPreferredSize().width, 50));

        tfPhoneNum = new JTextField(tfSize);
        tfPhoneNum.setFont(font);
        tfPhoneNum.setBorder(BorderFactory.createEmptyBorder());
        tfPhoneNum.setPreferredSize(new Dimension(tfPhoneNum.getPreferredSize().width, 50));

        btnLogin = new JRoundedButton("로그인", Palette.backgroundWhite, Palette.primaryBlue);
        btnLogin.setPreferredSize(btnSize);
        btnJoin = new JRoundedButton("회원가입", Palette.backgroundWhite, Palette.primaryBlue);
        btnJoin.setPreferredSize(btnSize);

        // JTextField 안에 표시될 초기 텍스트 설정
        String initialText1 = " 아이디";
        String initialText2 = " 전화번호";

        tfId.setText(initialText1);
        tfPhoneNum.setText(initialText2);

        tfId.setFocusable(false);
        tfPhoneNum.setFocusable(false);

        tfId.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfId.setFocusable(true);
            }
        });

        tfId.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfId.getText().equals(initialText1)) {
                    tfId.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfId.getText().isEmpty()) {
                    tfId.setText(initialText1);
                }
            }
        });

        tfPhoneNum.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfPhoneNum.setFocusable(true);
            }
        });
        tfPhoneNum.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfPhoneNum.getText().equals(initialText2)) {
                    tfPhoneNum.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfPhoneNum.getText().isEmpty()) {
                    tfPhoneNum.setText(initialText2);
                }
            }
        });

        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT,0,0);

        JPanel pnlId = new JPanel(flowLeft);
        pnlId.setBackground(Palette.backgroundWhiteAlt);
        pnlId.add(tfId);

        JPanel pnlPhoneNum = new JPanel(flowLeft);
        pnlPhoneNum.setBackground(Palette.backgroundWhiteAlt);
        pnlPhoneNum.add(tfPhoneNum);

        JPanel pnlGap = new JPanel();
        pnlGap.setBackground(Palette.backgroundWhiteAlt);

        pnlLogin = new JPanel(new GridLayout(0, 1,0,10));
        pnlLogin.add(pnlId);
        pnlLogin.add(pnlPhoneNum);
        pnlLogin.add(btnLogin);
        pnlLogin.add(btnJoin);
        pnlLogin.add(pnlGap);

        cardPanel.add("pnlLogin",pnlLogin);//레이아웃에 넣어주기
        cardLayout.show(cardPanel, "pnlLogin");//카드레이아웃 보여주기
        add(cardPanel);

        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "pnlJoin");
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 아이디칸이 비었을 경우
                if (tfId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginPanel.this,
                            "아이디를 입력하세요.",
                            "로그인폼",
                            JOptionPane.WARNING_MESSAGE);

                    // 존재하는 아이디일 경우
                } else if (users.contains(new User(tfId.getText()))) {

                    // 전화번호 칸이 비었을 경우
                    if (String.valueOf(tfPhoneNum.getText()).isEmpty()) {
                        JOptionPane.showMessageDialog(
                                LoginPanel.this,
                                "전화번호를 입력하세요.",
                                "로그인폼",
                                JOptionPane.WARNING_MESSAGE);

                        // 전화번호가 일치하지 않을 경우
                    } else if (!users.getUser(tfId.getText()).getPhoneNum().equals(String.valueOf(tfPhoneNum.getText()))) {
                        JOptionPane.showMessageDialog(
                                LoginPanel.this,
                                "전화번호가 일치하지 않습니다.");

                        // 다 완료될 경우(추가해야함!!!)
                    } else {
                        setVisible(false);
                        stream.User user = UserMgr.getInstance().find(tfPhoneNum.getText());
                        GUIMain.currentUser = user;
                        tfId.setText("");
                        tfPhoneNum.setText("");
                        GUIMain.login();

                    }
                    // 존재하지 않는 Id일 경우
                } else {
                    JOptionPane.showMessageDialog(
                            LoginPanel.this,
                            "존재하지 않는 Id입니다."

                    );

                }
            }
        });
    }


    //회원가입 패널
    public void setDisplay_join () {
        //초기화
        JTextField tfName;
        JTextField tfId;
        JTextField tfPhoneNum;
        JRoundedButton btnLogin;
        JRoundedButton btnJoin;

        tfName = new JTextField(tfSize);
        tfName.setFont(font);
        tfName.setBorder(BorderFactory.createEmptyBorder());
        tfName.setPreferredSize(new Dimension(tfName.getPreferredSize().width, 50));

        tfId = new JTextField(tfSize);
        tfId.setFont(font);
        tfId.setBorder(BorderFactory.createEmptyBorder());
        tfId.setPreferredSize(new Dimension(tfId.getPreferredSize().width, 50));

        tfPhoneNum = new JTextField(tfSize);
        tfPhoneNum.setFont(font);
        tfPhoneNum.setBorder(BorderFactory.createEmptyBorder());
        tfPhoneNum.setPreferredSize(new Dimension(tfPhoneNum.getPreferredSize().width, 50));

        btnLogin = new JRoundedButton("로그인", Palette.backgroundWhite, Palette.primaryBlue);
        btnLogin.setPreferredSize(btnSize);
        btnJoin = new JRoundedButton("회원가입", Palette.backgroundWhite, Palette.primaryBlue);
        btnJoin.setPreferredSize(btnSize);
        btnCancel = new JRoundedButton("취소", Palette.backgroundWhite, Palette.primaryBlue);
        btnCancel.setPreferredSize(btnSize);

        // JTextField 안에 표시될 초기 텍스트 설정
        String initialText1 = " 아이디";
        String initialText2 = " 전화번호";
        String initialText3 = " 이름";

        tfId.setText(initialText1);
        tfPhoneNum.setText(initialText2);
        tfName.setText(initialText3);

        tfId.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfId.setFocusable(true);
            }
        });

        tfId.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfId.getText().equals(initialText1)) {
                    tfId.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfId.getText().isEmpty()) {
                    tfId.setText(initialText1);
                }
            }
        });

        tfPhoneNum.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfPhoneNum.setFocusable(true);
            }
        });
        tfPhoneNum.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfPhoneNum.getText().equals(initialText2)) {
                    tfPhoneNum.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfPhoneNum.getText().isEmpty()) {
                    tfPhoneNum.setText(initialText2);
                }
            }
        });

        tfName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfName.setFocusable(true);
            }
        });

        tfName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfName.getText().equals(initialText3)) {
                    tfName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfName.getText().isEmpty()) {
                    tfName.setText(initialText3);
                }
            }
        });

        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT, 0, 0);

        JPanel pnlId = new JPanel(flowLeft);
        pnlId.setBackground(Palette.backgroundWhiteAlt);
        pnlId.add(tfId);

        JPanel pnlPhoneNum = new JPanel(flowLeft);
        pnlPhoneNum.setBackground(Palette.backgroundWhiteAlt);
        pnlPhoneNum.add(tfPhoneNum);

        JPanel pnlName = new JPanel(flowLeft);
        pnlPhoneNum.setBackground(Palette.backgroundWhiteAlt);
        pnlName.add(tfName);

        pnlJoin = new JPanel(new GridLayout(0, 1, 0, 10));
        pnlJoin.add(pnlId);
        pnlJoin.add(pnlName);
        pnlJoin.add(pnlPhoneNum);
        pnlJoin.add(btnJoin);
        pnlJoin.add(btnCancel);

        cardPanel.add("pnlJoin", pnlJoin);
        cardPanel.setBounds(635, 350, pnlJoin.getPreferredSize().width, pnlJoin.getPreferredSize().height);
        add(cardPanel);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(cardPanel,"pnlLogin");
                tfId.setText(initialText1);
                tfPhoneNum.setText(initialText2);
                tfName.setText(initialText3);
            }
        });
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                // 정보 하나라도 비어있으면
                if(tfId.getText().isEmpty()||tfName.getText().isEmpty()||tfPhoneNum.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            LoginPanel.this,
                            "모든 정보를 입력해주세요."
                    );
                    // 모두 입력했을 때
                } else {
                    // Id 중복일 때
                    if(users.isIdOverlap(tfId.getText())) {
                        JOptionPane.showMessageDialog(
                                LoginPanel.this,
                                "이미 존재하는 Id입니다."
                        );
                        tfId.requestFocus();
                    } else {
                        try {
                            users.addUsers(new User(
                                    tfId.getText(),
                                    tfName.getText(),
                                    tfPhoneNum.getText()
                            ));
                            stream.User user = new stream.User();
                            user.read(tfId.getText(),
                                    tfName.getText(),
                                    tfPhoneNum.getText());
                            UserMgr.getInstance().mList.add(user);
                            cardLayout.show(cardPanel, "pnlLogin");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        JOptionPane.showMessageDialog(
                                LoginPanel.this,
                                "회원가입을 완료했습니다!"


                        );
                    }
                }
            }
        });
    }

}
