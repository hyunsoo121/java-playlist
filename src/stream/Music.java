package stream;

import facade.UIData;
import manager.Manageable;
import mgr.AlbumMgr;
import mgr.ArtistMgr;
import mgr.MusicMgr;

import java.util.Scanner;

public class Music implements Manageable, UIData {
    static int num = 0;
    int id = 0;
    String title;
    String name;
    Album album;
    String albumTitle;
    int views = 0;

    int time = 10;

    @Override
    public void read(Scanner sc) {
        String inputString = sc.nextLine();
        String[] parts = inputString.split(" 0 ");
        title = parts[0];
        name = parts[1];
        albumTitle = parts[2];
        time = Integer.parseInt(parts[3]);
        views = Integer.parseInt(parts[4]);
        album = AlbumMgr.getInstance().find(albumTitle);
        if (album != null)
            album.musicList.add(this);
        Artist artist = ArtistMgr.getInstance().find(name);
        if (artist != null)
            artist.musicList.add(this);
        id = ++num;

    }

    @Override
    public void print() {
        System.out.printf("[%d] 제목: %s 가수명: %s 앨범명: %s 재생수: %d, 앨범 커버: %s\n",
                id, title, name, albumTitle, views, getInfo(3));
    }

    @Override
    public boolean matches(String kwd) {
        if ((id + "").equals(kwd))
            return true;
        if (title.contains(kwd))
            return true;
        if (name.contains(kwd))
            return true;
        if (albumTitle.equals(kwd))
            return true;
        return false;
    }

    public boolean matchesId(String id) {
        if ((this.id + "").equals(id))
            return true;
        return false;
    }

    public String getInfo(int flag) {
        if (flag == 1) {
            return title;
        }
        if (flag == 2) {
            return album.releaseDate;
        }
        if (flag == 3) {
            return album.imagePath;
        }
        if (flag == 4) {
            return album.genre;
        }
        if (flag == 5) {
            return name;
        }
        if (flag == 6) {
            return time/60+"분"+time%60+"초";
        }
        if (flag == 7) {
            return (""+time);
        }
        else
            return null;
    }

    public static Music findById(String kwd) {
        for (Music m : MusicMgr.getInstance().mList) {
            if (m.matchesId(kwd))
                return m;
        }
        return null;
    }

    @Override
    public void set(Object[] uitexts) {

    }

    @Override
    public String[] getUiTexts() {
        String[] texts = new String[5];
        texts[0] = "" + id;
        texts[1] = title;
        texts[2] = name;
        texts[3] = albumTitle;
        texts[4] = "" + views;
        return texts;
    }

    @Override
    public String getImagePath() {
        // TODO Auto-generated method stub
        return album.imagePath;
    }
}
