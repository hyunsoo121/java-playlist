import java.util.ArrayList;
import java.util.Scanner;

public class User implements Manageable {
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

    @Override
    public void print() {
        System.out.printf("사용자: %s 전화번호: %s 아이디: %s\n",
                name, tel, id);
    }

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

    public void printLibrary() {
        System.out.format("%s님의 전체 플레이리스트 목록(%d개)\n", name, library.size());
        for (Playlist p : library) {
            p.print();
            p.printDetails();
        }
    }

    public void addToLibrary() {
        Playlist list = new Playlist();
        System.out.print("플레이리스트 제목을 입력하세요 : ");
        String title = sc.next();
        if (searchLibrary(title) != null) {
            System.out.print("이미 존재하는 제목입니다. 다시 시도해주세요.\n");
        } else {
            list.create(title);
            library.add(list);
        }
    }

    public void deleteFromLibrary(Playlist p) {
        library.remove(p);
    }

    public Playlist searchLibrary(String kwd) {
        for (Playlist p : library) {
            if (p.matches(kwd)) return p;
        }
        return null;
    }

    public void addMusic(Playlist p) {
        System.out.print("추가할 음악(제목, 번호 등) : ");
        String title = sc.next();
        Music music = findMusic(title);
        if (music != null) {
            p.add(music);
        }
    }

    public void deleteMusic(Playlist p) {
        System.out.print("삭제할 음악(제목, 번호 등) : ");
        String title = sc.next();
        Music music = findMusic(title);
        if (music != null) {
            p.delete(music);
        }
    }

    public Music findMusic(String kwd) {
        Manager<Music> manager = Stream.musicMgr;
        for (Music m : manager.mList) {
            if (m.matchesId(kwd)) return m;
        }
        return null;
    }
}