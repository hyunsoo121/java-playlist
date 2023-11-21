package login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginGUI extends JFrame {
    private UserDataSet users = new UserDataSet();

    private JLabel lblId;
    private JLabel lblPhoneNum;
    private JTextField tfId;
    private JTextField tfPhoneNum;
    private JButton btnLogin;
    private JButton btnJoin;
    private JLabel lblTitle;


    public LoginGUI() {
        users.readAll("user.txt");
        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    public void init() {
        // 사이즈 통합
        Dimension lblSize1 = new Dimension(28, 30);
        Dimension lblSize2 = new Dimension(33, 30);
        int tfSize = 15;
        Dimension btnSize = new Dimension(100, 25);

        lblId = new JLabel("ID : ");
        lblId.setPreferredSize(lblSize1);
        lblPhoneNum = new JLabel("Tel : ");
        lblPhoneNum.setPreferredSize(lblSize2);

        tfId = new JTextField(tfSize);
        tfPhoneNum = new JTextField(tfSize);

        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(btnSize);
        btnJoin = new JButton("Join");
        btnJoin.setPreferredSize(btnSize);

        lblTitle = new JLabel("Playlist");
        Font font = new Font("Arial", Font.BOLD, 70); // Arial 폰트, 볼드 스타일
        lblTitle.setFont(font);
    }

    public UserDataSet getUsers() {
        return users;
    }

    public String getTfId() {
        return tfId.getText();
    }

    public void setDisplay() {
        //이미지 추가
        ImageIcon icon = new ImageIcon("./res/music3.jpg");
        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(3000,1000,Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon((img));
        JLabel lbl = new JLabel(changeIcon);
        add(lbl);


        // FlowLayout 오른쪽 정렬
        FlowLayout flowRight = new FlowLayout(FlowLayout.RIGHT);

        // pnlNorth(pnlId, pnlPw)
        JPanel pnlNorth = new JPanel(new GridLayout(0, 1));
        JPanel pnlId = new JPanel(flowRight);
        pnlId.add(lblId);
        pnlId.add(tfId);

        JPanel pnlPhoneNum = new JPanel(flowRight);
        pnlPhoneNum.add(lblPhoneNum);
        pnlPhoneNum.add(tfPhoneNum);

        pnlNorth.add(pnlId);
        pnlNorth.add(pnlPhoneNum);

        JPanel pnlSouth = new JPanel(flowRight);
        pnlSouth.add(btnLogin);
        pnlSouth.add(btnJoin);

        JPanel pnlLogin = new JPanel(new GridLayout(0, 1));
        pnlLogin.add(pnlNorth);
        pnlLogin.add(pnlSouth);

        JPanel pnlFull = new JPanel(new GridLayout(1,0,10,0));
        pnlFull.add(lblTitle);
        pnlFull.add(pnlLogin);



        pnlLogin.setBorder(new EmptyBorder(85,0,30,100));
        pnlFull.setBorder(new EmptyBorder(0,200,0,0));
        add(pnlFull,BorderLayout.NORTH);

    }

    public void addListeners() {

        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                new JoinGUI(LoginGUI.this);
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
                    if(String.valueOf(tfPhoneNum.getText()).isEmpty()) {
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

    public void showFrame() {
        setTitle("Login");
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}

