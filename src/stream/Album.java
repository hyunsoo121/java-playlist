package stream;

import facade.UIData;
import manager.Manageable;
import mgr.ArtistMgr;

import java.util.ArrayList;
import java.util.Scanner;

public class Album implements Manageable, UIData {
    public String albumTitle;
    String artistName;
    String releaseDate;
    String genre;
    String imagePath;

    ArrayList<Music> musicList = new ArrayList<>();

    @Override
    public void read(Scanner sc) {
        albumTitle = "";
        while (sc.hasNext()) {
            String token = sc.next();
            if (token.equals("0")) {
                break; // 0을 만나면 루프를 종료
            }

            albumTitle += token + " "; // 문자열 name에 추가
        }
        albumTitle = albumTitle.trim();

        artistName = "";
        while (sc.hasNext()) {
            String token = sc.next();
            if (token.equals("0")) {
                break; // 0을 만나면 루프를 종료
            }
            artistName += token + " "; // 문자열 name에 추가
        }
        Artist artist = ArtistMgr.getInstance().find(artistName);
        if (artist != null)
            artist.albumList.add(this);
        releaseDate = sc.next();
        genre = sc.next();
        imagePath = sc.next();
    }

    @Override
    public void print() {
        System.out.format("앨범명: %s 가수명: %s 출시일: %s 장르: %s 앨범이미지: %s\n",
                albumTitle, artistName, releaseDate, genre, imagePath);
    }

    @Override
    public boolean matches(String kwd) {
        if (albumTitle.contains(kwd))
            return true;
        return false;
    }

    @Override
    public void set(Object[] uitexts) {

    }

    @Override
    public String[] getUiTexts() {
        String[] texts = new String[5];
        texts[0] = albumTitle;
        texts[1] = artistName;
        texts[2] = releaseDate;
        texts[3] = genre;
        texts[4] = "" + musicList.size();
        return texts;
    }

    public ArrayList<Music> getMusicList() {
        return musicList;
    }

    @Override
    public String getImagePath() {
        // TODO Auto-generated method stub
        return imagePath;
    }
}
