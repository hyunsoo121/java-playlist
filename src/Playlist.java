import java.util.ArrayList;

public class Playlist {
    static int n = 0;
    int num;
    String name;
    ArrayList<Music> songs;

    public Playlist(String name){
        this.num = ++n;
        this.name = name;
        this.songs = new ArrayList<>();
    }

}
