package gui;

import mgr.MusicMgr;
import stream.Music;
import ui_config.JRoundedButton;
import ui_config.JText;
import ui_config.Palette;

import javax.swing.*;
import java.awt.*;

public class ListPanel extends JPanel {
    public ListPanel(int page){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Palette.backgroundWhite);
        switch (page) {
            case 1:
                JText text1 = new JText("테스트입니다");
                add(text1);
                JText text2 = new JText("테스트입니다", 700, 30);
                add(text2);
                JRoundedButton rb1 = new JRoundedButton("기본 색 버튼");
                add(rb1);
                JRoundedButton rb2 = new JRoundedButton("색 변경 버튼", Palette.backgroundWhite, Palette.primaryBlue);
                add(rb2);

                JPanel testList = new JPanel();
                testList.setBackground(Palette.backgroundWhite);

                DefaultListModel<Music> listModel = new DefaultListModel<>();
                for (Music music : MusicMgr.getInstance().mList) {
                    listModel.addElement(music);
                }

                JList<Music> jList = new JList<>(listModel);
                jList.setCellRenderer(new ItemRenderer());
                jList.setBorder(BorderFactory.createEmptyBorder());
                jList.setBackground(Palette.backgroundWhite);

                JScrollPane scrollPane = new JScrollPane(jList);
                scrollPane.setBackground(Palette.backgroundWhite);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());
                scrollPane.setPreferredSize(new Dimension(900,500));

                testList.add(scrollPane, BorderLayout.CENTER);
                add(testList);
                break;
            case 2:
                JText text3 = new JText("카드 테스트", 700, 30);
                add(text3);
                break;
            case 3:
                JText text4 = new JText("카드 테스트", 400, 40);
                add(text4);
                JText text5 = new JText("텍스트 테스트", 400, 50);
                add(text5);

                JText txt1 = new JText("원하는 텍스트를", 400, 20);
                add(txt1);
                JRoundedButton btn2 = new JRoundedButton("적어주세요");
                add(btn2);
                break;
            default:
        }
    }
}
