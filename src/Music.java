import java.util.Scanner;

public class Music implements Manageable{
    String title;
    String name;
    String albumTitle;
    int views = 0;

    @Override
    public void read(Scanner sc) {
        String inputString = sc.nextLine();
        String[] parts = inputString.split(" 0 ");
        title = parts[0];
        name = parts[1];
        albumTitle = parts[2];
        Album album = (Album) Stream.albumMgr.find(albumTitle);
        if (album != null)
            album.musicList.add(this);
        Artist artist = (Artist) Stream.artistMgr.find(name);
        if (artist != null)
            artist.musicList.add(this);
    }

    @Override
    public void print() {
        System.out.printf("제목: %s 가수명: %s 앨범명: %s 재생수: %d\n", title, name, albumTitle, views);
    }

    @Override
    public boolean matches(String kwd) {
        if (title.equals(kwd))
            return true;
        return false;
    }
}
