import java.util.ArrayList;
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
        menu();
    }

    private void menu() {
        int num;
        while (true){
            System.out.print("(1)출력 (2)검색(미완성) (3)플레이리스트 (기타) 종료 ");
            num = sc.nextInt();
            if (num <1 || num > 3) break;
            switch (num){
                case 1 : printMenu(); break;
                case 2 : break;
                case 3 : playListMenu(); break;
                default: break;
            }
        }
    }

    private void printMenu() {
        int num;
        while (true){
            System.out.print("(1)아티스트출력 (2)앨범출력 (3)음악출력 (기타) 종료 ");
            num = sc.nextInt();
            if (num <1 || num > 3) break;
            switch (num){
                case 1 : artistMgr.printAll(); break;
                case 2 : albumMgr.printAll(); break;
                case 3 : musicMgr.printAll(); break;
                default: break;
            }
        }
    }
    private void playListMenu() {
        int num;
        while (true){
            System.out.print("(1)플레이리스트 출력  (2)플레이리스트에 추가 (3)플레이리스트에서 삭제 (기타) 종료 ");
            num = sc.nextInt();
            if (num <1 || num > 3) break;
            switch (num){
                case 1 : musicMgr.printPlayList(); break;
                case 2 : musicMgr.printAll(); musicMgr.addPlaylist(); break;
                case 3 : musicMgr.printPlayList(); musicMgr.deletePlayList(); break;
                default: break;
            }
        }

    }


    public static void main(String[] args){
        Stream stream = new Stream();
        stream.run();
    }
}
