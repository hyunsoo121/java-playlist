import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager<T extends Manageable> {
    public ArrayList<T> mList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void readAll(String filename, Factory<T> fac) {
        Scanner filein = openFile(filename);
        while (filein.hasNext()) {
            T t = fac.create();
            t.read(filein);
            mList.add(t);
        }
        filein.close();
    }

    void printAll() {
        for (T t : mList) {
            t.print();
        }
    }

    void search() {
        String kwd = null;
        kwd = sc.next();
        for (T t : mList) {
            if (t.matches(kwd))
                t.print();
        }
    }

    Scanner openFile(String filename) {
        Scanner filein = null;
        try {
            filein = new Scanner(new File(filename));
        } catch (IOException e) {
            System.out.println("파일 입력 오류");
            System.exit(0);
        }
        return filein;
    }

    public T find(String kwd) {
        // TODO Auto-generated method stub
        for (T t : mList) {
            if (t.matches(kwd))
                return t;
        }
        return null;
    }

//    public void printPlayList() {
//        for (T t : mList) {
//            t.print();
//        }
//    }

//    public void addPlaylist() {
//        System.out.print("플레이리스트에 추가할 노래의 번호를 입력해주세요 : ");
//        String id = sc.next();
//        for (T t : mList) {
//            Music m = (Music)t;
//            if (m.matchesId(id)) {
//                mList.add(m);
//            }
//        }
//    }

//    public void deletePlayList() {
//        System.out.print("플레이리스트에서 삭제할 노래의 번호를 입력해주세요 : ");
//        String id = sc.next();
//        for (T t : mList) {
//            Music m = (Music)t;
//            if (m.matchesId(id)) {
//                mList.remove(m);
//            }
//        }
//
//    }
}
