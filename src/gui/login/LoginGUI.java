package gui.login;


import ui_config.JRoundedButton;
import ui_config.JText;
import ui_config.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginGUI extends JFrame {
    private UserDataSet users = new UserDataSet();



    //private LoginGUI owner;
    //private UserDataSet users;
    //users = owner.getUsers();


    private JRoundedButton btnCancel;

    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);
    JPanel pnlJoin;
    JPanel pnlLogin;
    private String currentPanel;
    Dimension lblSize1 = new Dimension(28, 30);
    Dimension lblSize2 = new Dimension(33, 30);
    int tfSize = 18;
    Dimension btnSize = new Dimension(100, 25);

    Font font = new Font("LINE Seed Sans KR Regular", Font.PLAIN, 20);


    public LoginGUI() {
        users.readAll("user.txt");
        setDisplay_logo();
        setDisplay_login();
        setDisplay_join();
        //SelectCard();
        //addListeners();
        //addListeners2();
        showFrame();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int choice = JOptionPane.showConfirmDialog(
                        LoginGUI.this,
                        "로그인 프로그램을 종료합니다.",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
/*
    public UserDataSet getUsers() {
        return users;
    }
    public String getTfId() {
        return tfId.getText();
    }

 */

    public void setDisplay_logo(){
        ImageIcon icon = new ImageIcon("./res/000.png");
        Image img = icon.getImage();
        ImageIcon changeIcon = new ImageIcon((img));
        JLabel lbl = new JLabel(changeIcon);
        setLayout(null);
        lbl.setBounds(650, 200, 250, 100);
        add(lbl);
        //설명 추가
        JText lblexplain = new JText("소개문구", 700, 30);
        lblexplain.setBounds(690, 260,250,100);
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

        btnLogin = new JRoundedButton("Login", Palette.backgroundWhite, Palette.primaryBlue);
        btnLogin.setPreferredSize(btnSize);
        btnJoin = new JRoundedButton("회원가입", Palette.backgroundWhite, Palette.primaryBlue);
        btnJoin.setPreferredSize(btnSize);

        // JTextField 안에 표시될 초기 텍스트 설정
        String initialText1 = "아이디";
        String initialText2 = "전화번호";

        tfId.setText(initialText1);
        tfPhoneNum.setText(initialText2);

        tfId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // 키를 입력받을 때, 초기 텍스트를 삭제
                if (tfId.getText().equals(initialText1)) {
                    tfId.setText("");
                }
            }
        });
        tfPhoneNum.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // 키를 입력받을 때, 초기 텍스트를 삭제
                if (tfPhoneNum.getText().equals(initialText2)) {
                    tfPhoneNum.setText("");
                }
            }
        });

        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT,0,0);

        //pnlLogin.setBackground(Palette.primaryGray);

        JPanel pnlId = new JPanel(flowLeft);
        pnlId.setBackground(Palette.backgroundWhiteAlt);
        //pnlId.add(lblId);
        pnlId.add(tfId);

        JPanel pnlPhoneNum = new JPanel(flowLeft);
        pnlPhoneNum.setBackground(Palette.backgroundWhiteAlt);
        //pnlPhoneNum.add(lblPhoneNum);
        pnlPhoneNum.add(tfPhoneNum);

        pnlLogin = new JPanel(new GridLayout(0, 1,0,10));
        pnlLogin.add(pnlId);
        pnlLogin.add(pnlPhoneNum);
        pnlLogin.add(btnLogin);
        pnlLogin.add(btnJoin);
        add(pnlLogin);
        cardPanel.add("pnlLogin",pnlLogin);//레이아웃에 넣어주기
        cardLayout.show(cardPanel, "pnlLogin");//카드레이아웃 보여주기

        cardPanel.setBounds(635,350,pnlLogin.getPreferredSize().width,pnlLogin.getPreferredSize().height);
        add(cardPanel);

        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "pnlJoin");

                tfId.setText("");
                tfPhoneNum.setText("");
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 아이디칸이 비었을 경우
                if (tfId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginGUI.this,
                            "아이디를 입력하세요.",
                            "로그인폼",
                            JOptionPane.WARNING_MESSAGE);

                    // 존재하는 아이디일 경우
                } else if (users.contains(new User(tfId.getText()))) {

                    // 전화번호 칸이 비었을 경우
                    if (String.valueOf(tfPhoneNum.getText()).isEmpty()) {
                        JOptionPane.showMessageDialog(
                                LoginGUI.this,
                                "전화번호를 입력하세요.",
                                "로그인폼",
                                JOptionPane.WARNING_MESSAGE);

                        // 전화번호가 일치하지 않을 경우
                    } else if (!users.getUser(tfId.getText()).getPhoneNum().equals(String.valueOf(tfPhoneNum.getText()))) {
                        JOptionPane.showMessageDialog(
                                LoginGUI.this,
                                "전화번호가 일치하지 않습니다.");

                        // 다 완료될 경우(추가해야함!!!)
                    } else {


                        setVisible(false);
                        tfId.setText("");
                        tfPhoneNum.setText("");
                    }
                    // 존재하지 않는 Id일 경우
                } else {
                    JOptionPane.showMessageDialog(
                            LoginGUI.this,
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

        btnLogin = new JRoundedButton("Login", Palette.backgroundWhite, Palette.primaryBlue);
        btnLogin.setPreferredSize(btnSize);
        btnJoin = new JRoundedButton("회원가입", Palette.backgroundWhite, Palette.primaryBlue);
        btnJoin.setPreferredSize(btnSize);
        btnCancel = new JRoundedButton("취소", Palette.backgroundWhite, Palette.primaryBlue);
        btnCancel.setPreferredSize(btnSize);

        // JTextField 안에 표시될 초기 텍스트 설정
        String initialText1 = "아이디";
        String initialText2 = "전화번호";
        String initialText3 = "이름";

        tfId.setText(initialText1);
        tfPhoneNum.setText(initialText2);
        tfName.setText(initialText3);

        tfId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // 키를 입력받을 때, 초기 텍스트를 삭제
                if (tfId.getText().equals(initialText1)) {
                    tfId.setText("");
                }
            }
        });
        tfPhoneNum.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // 키를 입력받을 때, 초기 텍스트를 삭제
                if (tfPhoneNum.getText().equals(initialText2)) {
                    tfPhoneNum.setText("");
                }
            }
        });
        tfName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // 키를 입력받을 때, 초기 텍스트를 삭제
                if (tfName.getText().equals(initialText3)) {
                    tfName.setText("");
                }
            }
        });



        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT, 0, 0);

        JPanel pnlId = new JPanel(flowLeft);
        pnlId.setBackground(Palette.backgroundWhiteAlt);
        //pnlId.add(lblId);
        pnlId.add(tfId);

        JPanel pnlPhoneNum = new JPanel(flowLeft);
        pnlPhoneNum.setBackground(Palette.backgroundWhiteAlt);
        //pnlPhoneNum.add(lblPhoneNum);
        pnlPhoneNum.add(tfPhoneNum);

        JPanel pnlName = new JPanel(flowLeft);
        pnlPhoneNum.setBackground(Palette.backgroundWhiteAlt);
        //pnlPhoneNum.add(lblPhoneNum);
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
            }
        });
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                // 정보 하나라도 비어있으면
                if(tfId.getText().isEmpty()||tfName.getText().isEmpty()||tfPhoneNum.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            gui.login.LoginGUI.this,
                            "모든 정보를 입력해주세요."
                    );
                    // 모두 입력했을 때
                } else {
                    // Id 중복일 때
                    if(users.isIdOverlap(tfId.getText())) {
                        JOptionPane.showMessageDialog(
                                gui.login.LoginGUI.this,
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
                            cardLayout.show(cardPanel, "pnlLogin");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        JOptionPane.showMessageDialog(
                                gui.login.LoginGUI.this,
                                "회원가입을 완료했습니다!"


                        );
                    }
                }
            }
        });
    }

    public void showFrame () {
        setTitle("Login");
        pack();
        getContentPane().setBackground(Palette.backgroundWhiteAlt);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

        public static void main (String[]args){
            new LoginGUI();
        }
}
