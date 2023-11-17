import java.util.Scanner;

public class Music implements Manageable {
    static int num = 0;
    int id = 0;
    String title;
    String name;
    Album album;
    String albumTitle;
    int views = 0;

    @Override
    public void read(Scanner sc) {
        String inputString = sc.nextLine();
        String[] parts = inputString.split(" 0 ");
        title = parts[0];
        name = parts[1];
        albumTitle = parts[2];
        views = Integer.parseInt(parts[3]);
        album = Stream.albumMgr.find(albumTitle);
        if (album != null)
            album.musicList.add(this);
        Artist artist = Stream.artistMgr.find(name);
        if (artist != null)
            artist.musicList.add(this);
        id = ++num;
    }

    @Override
    public void print() {
        System.out.printf("[%d] 제목: %s 가수명: %s 앨범명: %s 재생수: %d, 앨범 커버: %s\n",
                id, title, name, albumTitle, views, albumInfo(3));
    }

    @Override
    public boolean matches(String kwd) {
        if ((id + "").equals(kwd)) return true;
        if (title.contains(kwd)) return true;
        if (name.contains(kwd)) return true;
        return false;
    }

    public boolean matchesId(String id) {
        if ((this.id + "").equals(id))
            return true;
        return false;
    }

    public String albumInfo(int flag) {
        if (flag == 1) {
            return album.releaseDate;
        }
        if (flag == 2) {
            return album.genre;
        }
        if (flag == 3) {
            return album.imagePath;
        } else return null;
    }

    public static Music findById(String kwd) {
        Manager<Music> manager = Stream.musicMgr;
        for (Music m : manager.mList) {
            if (m.matchesId(kwd)) return m;
        }
        return null;
    }

}
