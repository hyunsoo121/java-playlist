import java.util.ArrayList;
import java.util.Scanner;

public class Album implements Manageable{
    String albumTitle;
    String artistName;
    String releaseDate;
    String genre;

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
        Artist artist = (Artist) Stream.artistMgr.find(artistName);
        if (artist != null)
            artist.albumList.add(this);
        releaseDate = sc.next();
        genre = sc.next();
    }

    @Override
    public void print() {
        System.out.printf("앨범명: %s 가수명: %s 출시일: %s 장르: %s\n", albumTitle, artistName, releaseDate, genre);
    }

    @Override
    public boolean matches(String kwd) {
        if (albumTitle.equals(kwd))
            return true;
        return false;
    }
}
