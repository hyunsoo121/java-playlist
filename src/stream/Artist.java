package stream;

import facade.UIData;
import manager.Manageable;
import java.util.ArrayList;
import java.util.Scanner;

public class Artist implements Manageable, UIData {
    String name;
    String debutDate;
    String type;
    String agency;
    String imagePath;

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
        imagePath = sc.next();
    }

    @Override
    public void print() {
        System.out.printf("가수명: %s 데뷔일자: %s 유형: %s 회사: %s, 이미지: %s\n",
                name, debutDate, type, agency, imagePath);
    }

    @Override
    public boolean matches(String kwd) {
        if (name.contains(kwd))
            return true;
        return false;
    }

    @Override
    public void set(Object[] uitexts) {

    }

    @Override
    public String[] getUiTexts() {
        String[] texts = new String[5];
        texts[0] = name;
        texts[1] = debutDate;
        texts[2] = type;
        texts[3] = agency;
        return texts;
    }

    @Override
    public String getImagePath() {
        // TODO Auto-generated method stub
        return imagePath;
    }
}
