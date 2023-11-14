import java.util.ArrayList;
import java.util.Scanner;

public class Stream {
    Scanner sc = new Scanner(System.in);
    static Manager<Music> musicMgr = new Manager<>();
    static Manager<Artist> artistMgr = new Manager<>();
    static Manager<Album> albumMgr = new Manager<>();

    ArrayList<User> uList = new ArrayList<>();

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
        signup();
        menu();
    }

    private void signup() {
        User user = new User();
        user.signup();
        uList.add(user);
    }

    private void menu() {
        int num;
        while (true) {
            System.out.print("(1)출력 (2)검색 (3)나의 플레이리스트 (기타)종료 ");
            num = sc.nextInt();
            if (num < 1 || num > 3)
                break;
            switch (num) {
                case 1:
                    printMenu();
                    break;
                case 2:
                    searchMenu();
                    break;
                case 3:
                    playListMenu();
                    break;
                default:
                    break;
            }
        }
    }

    private void printMenu() {
        int num;
        while (true) {
            System.out.print("(1)아티스트출력 (2)앨범출력 (3)음악출력 (기타) 종료 ");
            num = sc.nextInt();
            if (num < 1 || num > 3)
                break;
            switch (num) {
                case 1:
                    artistMgr.printAll();
                    break;
                case 2:
                    albumMgr.printAll();
                    break;
                case 3:
                    musicMgr.printAll();
                    break;
                default:
                    break;
            }
        }
    }

    private void searchMenu() {
        int num;
        while (true) {
            System.out.print("(1)아티스트 검색 (2)앨범 검색 (3)음악 검색 (기타)종료 ");
            num = sc.nextInt();
            if (num < 1 || num > 3)
                break;
            switch (num) {
                case 1:
                    System.out.print("아티스트 이름 입력: ");
                    artistMgr.search();
                    break;
                case 2:
                    System.out.print("앨범명 입력: ");
                    albumMgr.search();
                    break;
                case 3:
                    System.out.print("음악 입력: ");
                    musicMgr.search();
                    break;
                default:
                    break;
            }
        }
    }

    private void playListMenu() {
        int num;
        while (true) {
            User user;
            System.out.print("(1)플레이리스트 출력 (2)플레이리스트에 추가 (3)플레이리스트에서 삭제 (기타)종료 ");
            num = sc.nextInt();
            if (num < 1 || num > 3)
                break;
            switch (num) {
                case 1:
                    user = findUser();
                    if (user == null){
                        System.out.print("해당 유저가 없습니다.\n");
                        break;
                    }
                    user.printPlayList();
                    break;
                case 2:
                    int pn;
                    String name;
                    user = findUser();
                    if (user == null){
                        System.out.print("해당 유저가 없습니다.\n");
                        break;
                    }
                    System.out.print("(1)새로운 플레이리스트에 추가 (2)기존 플레이리스트에 추가 (기타)종료 ");
                    pn = sc.nextInt();
                    if (pn<1 || pn>2){
                        break;
                    }
                    else if (pn == 1){
                        System.out.print("생성할 플레이리스트의 이름을 입력해주세요>>");
                        name = sc.next();
                        user.createPlayList(name);
                        user.addPlaylist(musicMgr);
                        break;
                    }
                    else {
                        if (user.isPlayListEmpty()){
                            System.out.println("기존의 플레이리스트가 없습니다.");
                            break;
                        }
                        user.addPlaylist(musicMgr);
                        break;
                    }

                case 3:
                    user = findUser();
                    if (user == null){
                        System.out.print("해당 유저가 없습니다.\n");
                        break;
                    }
                    user.deletePlayList();
                    break;
                default:
                    break;
            }
        }

    }


    private User findUser() {
        User user = null;
        System.out.print("아이디를 입력하세요 : ");
        String name = sc.next();
        for (User u : uList){
            user = u.findUser(name);
        }
        return user;
    }


    public static void main(String[] args) {
        Stream stream = new Stream();
        stream.run();
    }
}
