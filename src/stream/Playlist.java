package stream;

import facade.UIData;
import manager.Manageable;
import mgr.PlaylistMgr;
import mgr.UserMgr;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Playlist implements Manageable, UIData {
    static DateTimeFormatter ymdFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    static int n = 0;
    Scanner sc = new Scanner(System.in);
    int num;
    String title;
    String date;
    User user;
    ArrayList<Music> musicList;

    public void create(String tel) {
        while (true) {
            String regex = "^\\d+$";
            String combined = tel + " ";
            System.out.format("제목을 입력하세요. : ");
            String title = sc.next();
            if (Pattern.matches(regex, title)) {
                System.out.println("제목을 숫자로만 구성할 수 없습니다.");
                continue;
            }
            combined += title + " ";
            combined += LocalDate.now().format(ymdFormat) + " ";
            Scanner playlistInfo = new Scanner(combined + "0");
            read(playlistInfo);
            break;
        }
    }

    public void create(String tel, String listName){
        num = ++n;
        User u = UserMgr.getInstance().find(tel);
        user = u;
        title = listName;
        date = LocalDate.now().format(ymdFormat);
        musicList = new ArrayList<>();
        user.library.add(this);
        PlaylistMgr.getInstance().read(this);
    }


    @Override
    public void read(Scanner sc) {
        num = ++n;
        String tel = sc.next();
        User u = UserMgr.getInstance().find(tel);
        if (u != null) {
            user = u;
        }
        title = sc.next();
        date = sc.next();
        musicList = new ArrayList<>();
        user.library.add(this);
        String musicId;
        while (true) {
            musicId = sc.next();
            if (musicId.equals("0")) {
                break;
            } else {
                add(musicId);
            }
        }

    }

    @Override
    public void print() {
        System.out.format("<%d> %s 플레이리스트 (생성일: %s), 사용자: %s\n",
                num, title, date, user.name);
    }

    @Override
    public boolean matches(String kwd) {
        if (title.equals(kwd)) {
            return true;
        } else {
            return Integer.toString(num).equals(kwd);
        }
    }

    public void printDetails() {
        System.out.format("(%d개의 트랙)\n", musicList.size());
        for (Music m : musicList) {
            m.print();
        }
    }

    public void add(String id) {
        boolean flag = true;
        Music m = Music.findById(id);
        if (m != null) {
            for (Music c : musicList) {
                if (c.id == m.id) {
                    System.out.format("이미 플레이리스트에 추가된 곡입니다.\n");
                    flag = false;
                }
            }
            if (flag)
                musicList.add(m);
        }
    }

    public void delete(String id) {
        Music m = Music.findById(id);
        if (m != null) {
            musicList.remove(m);
        }
    }

    @Override
    public void set(Object[] uitexts) {

    }

    @Override
    public String[] getUiTexts() {
        String[] texts = new String[5];
        texts[0] = "" + num;
        texts[1] = user.tel;
        texts[2] = title;
        texts[3] = date;
        texts[4] = "" + musicList.size();
        return texts;
    }

    public ArrayList<Music> getList() {
        return musicList;
    }

    @Override
    public String getImagePath() {
        // TODO Auto-generated method stub
        return null;
    }
}
