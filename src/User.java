import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class User{
    Scanner sc = new Scanner(System.in);
    String id;
    String name;
    String tel;
    Map<String, Playlist> playlists;


    public void signup() {
        System.out.print("아이디 : ");
        this.id = sc.nextLine();
        System.out.print("이름 : ");
        this.name = sc.nextLine();
        System.out.print("전화번호(010-2222-2222) : ");
        this.tel = sc.nextLine();
        this.playlists = new HashMap<>();
    }

    public boolean matches(String kwd) {
        return this.name.equals(kwd);
    }

    public User findUser(String name) {
        if (matches(name)){
            return this;
        }
        return null;
    }

    public void printPlayList() {
        Playlist pl = selectPlaylist();
        if (pl == null){
            System.out.println("플레이리스트가 비었습니다. 노래를 먼저 추가해주세요.");
            return;
        }
        for (Music m : pl.songs){
            m.print();
        }
    }

    public void addPlaylist(Manager<Music> musicMgr) {
        int n;
        Playlist pl = selectPlaylist();
        musicMgr.printAll();
        System.out.print("추가할 노래의 번호를 입력하세요 : ");
        n = sc.nextInt();
        int listSize = Stream.musicMgr.mList.size();
        if (n<1 || n>listSize){
            System.out.print("잘못된 번호를 입력하셨습니다. 다시 입력해주세요.\n");
            return ;
        }
        for (Music m : pl.songs){
            if(m.id == n){
                System.out.print("선택하신 음악이 이미 플레이리스트에 있습니다.\n");
                return ;
            }
        }
        Music m = Stream.musicMgr.findMusic(n);
        pl.songs.add(m);
    }

    public void deletePlayList() {
        int n;
        Playlist pl = selectPlaylist();
        if (pl == null) return;
        for (Music m : pl.songs){
            System.out.printf("<%s 플레이리스트>\n",pl.name);
            m.print();
        }
        System.out.print("삭제할 노래의 번호를 입력하세요 : ");
        n = sc.nextInt();
        int listSize = pl.songs.size();
        if (n < 1 || n>listSize){
            System.out.print("잘못된 번호를 입력하셨습니다. 다시 입력해주세요.\n");
            return ;
        }
        Music m = Stream.musicMgr.findMusic(n);
        pl.songs.remove(m);

    }

    public void createPlayList(String name) {
        Playlist newPlaylist = new Playlist(name);
        int num = newPlaylist.num;
        playlists.put(String.valueOf(num),newPlaylist);
    }

    public boolean isPlayListEmpty() {
        return playlists.isEmpty();
    }

    public Playlist selectPlaylist() {
        printPlaylistName();
        if (isPlayListEmpty()){
            System.out.println("플레이리스트가 없습니다. 플레이리스트를 먼저 만들어주세요.");
            return null;
        }
        while(true){
            int n;
            System.out.print("플레이리스트의 번호를 입력하세요 : ");
            n = sc.nextInt();
            for(String key : playlists.keySet()){
                if(key.equals(String.valueOf(n))){
                    return playlists.get((String.valueOf(n)));
                }
            }
            System.out.print("잘못된 번호를 입력하셨습니다. 다시 입력해주세요.\n");
        }
    }

    private void printPlaylistName() {
        System.out.println("<나의 플레이리스트 목록>");
        for(String key : playlists.keySet()) {
            Playlist pl = playlists.get(key);
            System.out.printf("[%s] %s\n",key,pl.name);
        }
    }
}
