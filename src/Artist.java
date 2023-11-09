import java.util.ArrayList;
import java.util.Scanner;

public class Artist implements Manageable{
    String name;
    String debutDate;
    String type;
    String agency;

    ArrayList<Album> albumList = new ArrayList<>();
    ArrayList<Music> musicList = new ArrayList<>();
    @Override
    public void read(Scanner sc) {
        name = "";
        while (sc.hasNext()) {
            String token = sc.next();
            if (token.equals("0")) {
                break; // 0을 만나면 루프를 종료
            }
            name += token + " "; // 문자열 name에 추가
        }
        debutDate = sc.next();
        type = sc.next();
        agency = sc.next();
    }

    @Override
    public void print() {
        System.out.printf("가수명: %s 데뷔일자: %s 유형: %s 회사: %s\n", name, debutDate, type, agency);
    }

    @Override
    public boolean matches(String kwd) {
        if (name.equals(kwd))
            return true;
        return false;
    }
}
