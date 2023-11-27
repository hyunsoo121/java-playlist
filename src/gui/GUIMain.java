package gui;

import stream.Stream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIMain {
    private static Point draggable;
    static Dimension mainSize = new Dimension(1200, 800);

    public static void main(String[] args) {
        Stream.getInstance().run();
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("음악 스트리밍 Colon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        JPanel mainPanel = new JPanel(new CardLayout());

        HomePanel homePanel = new HomePanel();
        homePanel.setSize(frame.getWidth(), frame.getHeight());
        PlayerPanel playerPanel = new PlayerPanel();
        playerPanel.setSize(frame.getWidth(), frame.getHeight());
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setSize(frame.getWidth(), frame.getHeight());

        mainPanel.add(homePanel, "home");
//        mainPanel.add(playerPanel, "player");
//        mainPanel.add(loginPanel, "login");


        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                draggable = e.getPoint();
            }
        });
        frame.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point point = e.getLocationOnScreen();
                frame.setLocation(point.x - draggable.x, point.y - draggable.y);
            }
        });
        frame.add(mainPanel);
        mainPanel.setSize(mainSize);
        frame.setVisible(true);
    }
}
