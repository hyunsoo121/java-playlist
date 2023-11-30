package stream;

import manager.Factory;
import mgr.*;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Stream {
    Scanner sc = new Scanner(System.in);
    private static Stream stream = null;
    public static User currentUser;

    public static void main(String[] args) {
        Stream.getInstance().run();
    }

    public static Stream getInstance() {
        if (stream == null)
            stream = new Stream();
        return stream;
    }

    public void run() {
        ArtistMgr.getInstance().readAll("artist.txt", new Factory<Artist>() {
            @Override
            public Artist create() {
                return new Artist();
            }
        });

        AlbumMgr.getInstance().readAll("album.txt", new Factory<Album>() {
            @Override
            public Album create() {
                return new Album();
            }
        });

        MusicMgr.getInstance().readAll("music.txt", new Factory<Music>() {
            @Override
            public Music create() {
                return new Music();
            }
        });
        UserMgr.getInstance().readAll("user.txt", new Factory<User>() {
            @Override
            public User create() {
                return new User();
            }
        });
        PlaylistMgr.getInstance().readAll("playlist.txt", new Factory<Playlist>() {
            @Override
            public Playlist create() {
                return new Playlist();
            }
        });
        // MusicMgr.getInstance().printAll();
        // AlbumMgr.getInstance().printAll();
        // ArtistMgr.getInstance().printAll();
        // UserMgr.getInstance().printAll();
        // PlaylistMgr.getInstance().printAll();
        // sign();
    }

    private void sign() {
        while (true) {
            sc.useDelimiter("\n");
            User user = null;
            System.out.print("[콘솔] 전화번호를 입력하세요.(0 입력 시 종료) : ");
            String telPattern = "^\\d{3}-\\d{3,4}-\\d{4}$";
            String tel = sc.next();
            if (tel.equals("0"))
                break;
            if (!Pattern.matches(telPattern, tel)) {
                System.out.print("유효한 전화번호 형식이 아닙니다. 다음과 같은 형식으로 입력해주세요\n");
                System.out.print("ex) 010-1234-5678\n");
                continue;
            }
            user = UserMgr.getInstance().find(tel);
            if (user != null) {
                currentUser = user;
                System.out.format("%s님, 다시 만나서 반가워요.\n", user.name);
            } else {
                user = new User();
                System.out.format("%s 번호로 회원가입을 진행합니다.\n", tel);
                user.signUp(tel);
                UserMgr.getInstance().mList.add(user);
                currentUser = user;
            }
            menu();
        }
    }

    private void menu() {
        int num;
        while (true) {
            System.out.print("(1)출력 (2)검색 (3)나의 플레이리스트 (기타)로그아웃 ");
            try {
                num = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("올바른 정수를 입력하세요.");
                sc.nextLine();
                continue;
            }
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
                    currentUser.userMenu();
                    break;
                default:
                    break;
            }
        }
    }

    private void printMenu() {
        int num;
        int state;
        while (true) {
            System.out.print("(1)아티스트출력 (2)앨범출력 (3)음악출력 (기타)종료 ");
            try {
                num = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("올바른 정수를 입력하세요.");
                sc.nextLine();
                continue;
            }
            if (num < 1 || num > 3)
                break;
            switch (num) {
                case 1:
                    ArtistMgr.getInstance().printAll();
                    break;
                case 2:
                    AlbumMgr.getInstance().printAll();
                    break;
                case 3:
                    System.out.print("(1)인기순 정렬 (2)최신순 정렬 (기타)종료 ");
                    try {
                        state = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("올바른 정수를 입력하세요.");
                        sc.nextLine();
                        continue;
                    }
                    sortList(state);
                    MusicMgr.getInstance().printAll();
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
            try {
                num = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("올바른 정수를 입력하세요.");
                sc.nextLine();
                continue;
            }
            if (num < 1 || num > 3)
                break;
            switch (num) {
                case 1:
                    System.out.print("아티스트 이름 입력: ");
                    ArtistMgr.getInstance().search();
                    break;
                case 2:
                    System.out.print("앨범명 입력: ");
                    AlbumMgr.getInstance().search();
                    break;
                case 3:
                    System.out.print("음악 입력: ");
                    MusicMgr.getInstance().search();
                    break;
                default:
                    break;
            }
        }
    }

    public static void sortList(int state) {
        if (state == 1) {
            Collections.sort(MusicMgr.getInstance().mList, (a, b) -> b.views - a.views); // 재생수 차트
        } else if (state == 2) {
            Collections.sort(MusicMgr.getInstance().mList, (a, b) -> b.getInfo(2).compareTo(a.getInfo(1))); // 최신순 차트
        }
    }
}
