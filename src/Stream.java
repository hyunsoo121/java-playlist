import java.util.Scanner;

public class Stream {
    Scanner sc = new Scanner(System.in);
    Manager<Music> musicMgr = new Manager<>();
    static Manager<Artist> artistMgr = new Manager<>();
    static Manager<Album> albumMgr = new Manager<>();
    void run() {
        artistMgr.readAll("artist.txt", new Factory<Artist>() {
            @Override
            public Artist create() {
                return new Artist();
            }
        });

        albumMgr.readAll("album.txt", new Factory<Album>() {
            @Override
            public Album create() {
                return new Album();
            }
        });

        musicMgr.readAll("music.txt", new Factory<Music>() {
            @Override
            public Music create() {
                return new Music();
            }
        });
        artistMgr.printAll();
        albumMgr.printAll();
        musicMgr.printAll();
    }

    public static void main(String[] args){
        Stream stream = new Stream();
        stream.run();
    }
}
