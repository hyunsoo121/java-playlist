import java.util.Collections;
import java.util.Scanner;

public class Stream {
    static Manager<User> userMgr = new Manager<>();
    static Manager<Music> musicMgr = new Manager<>();
    static Manager<Artist> artistMgr = new Manager<>();
    static Manager<Album> albumMgr = new Manager<>();
    Scanner sc = new Scanner(System.in);
    User currentUser;

    public static void main(String[] args) {
        Stream stream = new Stream();
        stream.run();
    }

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
        userMgr.readAll("user.txt", new Factory<User>() {
            @Override
            public User create() {
                return new User();
            }
        });
        sign();
    }

    private void sign() {
        while (true) {
            User user = null;
            System.out.print("전화번호를 입력하세요.(0 입력 시 종료) : ");
            String tel = sc.next();
            if (tel.equals("0")) break;
            user = userMgr.find(tel);
            if (user != null) {
                currentUser = user;
                System.out.format("%s님, 다시 만나서 반가워요.\n", user.name);
            } else {
                user = new User();
                System.out.format("%s 번호로 회원가입을 진행합니다.\n", tel);
                user.signUp(tel);
                userMgr.mList.add(user);
                currentUser = user;
            }
            menu();
        }
        System.exit(0);
    }

    private void menu() {
        int num;
        while (true) {
            System.out.print("(1)출력 (2)검색 (3)나의 플레이리스트 (기타)로그아웃 ");
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
                    playlistMenu();
                    break;
                default:
                    break;
            }
        }
    }

    private void printMenu() {
        int num;
        while (true) {
            System.out.print("(1)아티스트출력 (2)앨범출력 (3)음악출력 (기타)종료 ");
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
                    System.out.print("(1)인기순 정렬 (2)최신순 정렬 (기타)종료 ");
                    int flag = sc.nextInt();
                    if (flag == 1) {
                        Collections.sort(musicMgr.mList, (a, b) -> b.views - a.views);
                        musicMgr.printAll();
                    } else if (flag == 2) {
                        Collections.sort(musicMgr.mList, (a, b) -> b.albumInfo(1).compareTo(a.albumInfo(1)));
                        musicMgr.printAll();
                    } else {
                        break;
                    }
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
                    break;
                default:
                    break;
            }
        }
    }

    private void playlistMenu() {
        int num;
        while (true) {
            System.out.print("(1)플레이리스트 출력 (2)플레이리스트 생성 (3)플레이리스트 수정 (기타)종료 ");
            num = sc.nextInt();
            if ((num < 1) || (num > 3))
                break;
            switch (num) {
                case 1:
                    currentUser.printLibrary();
                    break;
                case 2:
                    currentUser.addToLibrary();
                    break;
                case 3:
                    System.out.format("수정할 플레이리스트의 제목을 입력하세요.");
                    String kwd = sc.next();
                    Playlist p = currentUser.searchLibrary(kwd);
                    if (p != null) {
                        System.out.print("(1)음악 추가 (2)음악 삭제 (3)플레이리스트 삭제 (기타)종료 ");
                        int flag = sc.nextInt();
                        if (flag == 1) {
                            currentUser.addMusic(p);
                        } else if (flag == 2) {
                            currentUser.deleteMusic(p);
                        } else if (flag == 3) {
                            currentUser.deleteFromLibrary(p);
                        } else {
                            break;
                        }
                    } else {
                        System.out.format("검색 결과가 없습니다.");
                    }
                    break;
                default:
                    break;
            }
        }

    }
}
