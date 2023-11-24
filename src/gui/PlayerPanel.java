package gui;

import mgr.MusicMgr;
import stream.Music;

import javax.swing.*;
import java.util.ArrayList;

public class PlayerPanel extends JPanel {
    public PlayerPanel() {
        this(MusicMgr.getInstance().mList);
    }

    public PlayerPanel(ArrayList<Music> mList) {

    }
}
