package gui;

import stream.Music;
import stream.Stream;
import stream.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUIMain {
    private static Point draggable;
    static Dimension mainSize = new Dimension(1200, 800);

    static JPanel mainPanel = new JPanel(new CardLayout());

    static User currentUser = new User();

    public static void main(String[] args) {
        Stream.getInstance().run();
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("음악 스트리밍 Colon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setSize(frame.getWidth(), frame.getHeight());

        mainPanel.add(loginPanel, "login");


        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                draggable = e.getPoint();
            }
        });
        frame.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (draggable != null) {
                    Point point = e.getLocationOnScreen();
                    frame.setLocation(point.x - draggable.x, point.y - draggable.y);
                }
            }
        });
        frame.add(mainPanel);
        mainPanel.setSize(mainSize);
        frame.setVisible(true);

        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "login");
    }

    public static void manageList(ArrayList<Music> mList){
        PlayerPanel updatePlayerPanel = new PlayerPanel(mList);
        mainPanel.add(updatePlayerPanel, "player");

        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "player");
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void backToHome(){
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "home");
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void login(){
        HomePanel homePanel = new HomePanel(currentUser);
        mainPanel.add(homePanel, "home");
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "home");
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void logout() {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "login");
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}