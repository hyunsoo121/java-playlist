package stream;

import facade.UIData;
import manager.Manageable;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User implements Manageable, UIData {
    Scanner sc = new Scanner(System.in);
    String id;
    String name;
    String tel;
    ArrayList<Playlist> library;

    @Override
    public void read(Scanner sc) {
        tel = sc.next();
        id = sc.next();
        name = sc.next();
        library = new ArrayList<>();
    }

    public void read(String tel, String id, String name) {
        this.tel = tel;
        this.id = id;
        this.name = name;
        library = new ArrayList<>();
    }

    @Override
    public void print() {
        System.out.printf("사용자: %s, 전화번호: %s, 아이디: %s\n",
                name, tel, id);
        printUserLibrary();
    }

    @Override
    public boolean matches(String kwd) {
        if (tel.equals(kwd)) {
            return true;
        } else {
            return name.equals(kwd);
        }
    }

    public void signUp(String tel) {
        String combined = tel + " ";
        System.out.print("아이디 : ");
        combined += sc.next() + " ";
        System.out.print("이름 : ");
        combined += sc.next() + " ";
        Scanner signInfo = new Scanner(combined);
        read(signInfo);
        signInfo.close();
    }

    public void userMenu() {
        int num;
        while (true) {
            System.out.print("(1)플레이리스트 출력 (2)플레이리스트 생성 (3)플레이리스트 수정 (기타)종료 ");
            try {
                num = sc.nextInt();
            } catch (InputMismatchException e){
                System.out.println("올바른 정수를 입력하세요.");
                sc.nextLine();
                continue;
            }
            if ((num < 1) || (num > 3))
                break;
            switch (num) {
                case 1:
                    printUserLibrary();
                    break;
                case 2:
                    addPlaylist();
                    break;
                case 3:
                    System.out.format("수정할 플레이리스트의 제목 또는 번호를 입력하세요. : ");
                    String kwd = sc.next();
                    Playlist p = searchUserLibrary(kwd);
                    if (p != null) {
                        playlistMenu(p);
                    } else {
                        System.out.format("검색 결과가 없습니다.\n");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void playlistMenu(Playlist p) {
        int state;
        String id;
        while (true) {
            System.out.print("(1)음악 추가 (2)음악 삭제 (3)플레이리스트 삭제 (기타)종료 ");
            try {
                state = sc.nextInt();
            } catch (InputMismatchException e){
                System.out.println("올바른 정수를 입력하세요.");
                sc.nextInt();
                continue;
            }
            if (state < 1 || state > 3)
                break;
            switch (state) {
                case 1:
                    System.out.print("추가할 음악(id) : ");
                    id = sc.next();
                    p.add(id);
                    break;
                case 2:
                    System.out.print("삭제할 음악(id) : ");
                    id = sc.next();
                    p.delete(id);
                    break;
                case 3:
                    library.remove(p);
                    break;
                default:
                    break;
            }
        }
    }

    private void addPlaylist() {
        Playlist p = new Playlist();
        p.create(tel);
    }

    public void printUserLibrary() {
        System.out.format("%s님의 전체 플레이리스트 목록(%d개)\n", name, library.size());
        for (Playlist p : library) {
            p.print();
            p.printDetails();
        }
    }

    private Playlist searchUserLibrary(String kwd) {
        for (Playlist p : library) {
            if (p.matches(kwd))
                return p;
        }
        return null;
    }

    @Override
    public void set(Object[] uitexts) {

    }

    @Override
    public String[] getUiTexts() {
        String[] texts = new String[5];
        texts[0] = id;
        texts[1] = tel;
        texts[2] = name;
        texts[3] = "" + library.size();
        return texts;
    }

    @Override
    public String getImagePath() {
        // TODO Auto-generated method stub
        return null;
    }
}