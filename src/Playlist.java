import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Playlist implements Manageable {
    static DateTimeFormatter ymdFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    static int n = 0;
    int num;
    String title;
    String date;
    ArrayList<Music> musicList;

    public void create(String ttl) {
        String combined = ttl + " ";
        combined += LocalDate.now().format(ymdFormat);
        Scanner playlistInfo = new Scanner(combined);
        read(playlistInfo);
    }

    @Override
    public void read(Scanner sc) {
        num = ++n;
        title = sc.next();
        date = sc.next();
        this.musicList = new ArrayList<>();
    }

    @Override
    public void print() {
        System.out.format("플레이리스트 이름: %s, 생성일: %s, 번호: %d\n",
                title, date, num);
    }

    public void printDetails() {
        System.out.format("(트랙 %d개)\n", musicList.size());
        for (Music m : musicList) {
            m.print();
        }
    }

    @Override
    public boolean matches(String kwd) {
        if (title.equals(kwd)) {
            return true;
        } else {
            return Integer.toString(num).equals(kwd);
        }
    }

    public void add(Music m) {
        musicList.add(m);
    }

    public void delete(Music m) {
        musicList.remove(m);
    }

}
