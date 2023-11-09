import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager <T extends Manageable>{
    Scanner sc = new Scanner(System.in);
    public ArrayList <T> mList = new ArrayList<>();

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
        for (T t: mList) {
            t.print();
        }
    }
    void search() {
        String kwd = null;
        while (true) {
            System.out.print(">> ");
            kwd = sc.next();
            if (kwd.contentEquals("end"))
                break;
            for (T t: mList) {
                if (t.matches(kwd))
                    t.print();
            }
        }
    }
    Scanner openFile(String filename) {
        Scanner filein = null;
        try {
            filein = new Scanner(new File(filename));
        } catch (IOException e)
        {
            System.out.println("파일 입력 오류");
            System.exit(0);
        }
        return filein;
    }

    public T find(String kwd) {
        // TODO Auto-generated method stub
        for (T t: mList) {
            if (t.matches(kwd))
                return t;
        }
        return null;
    }
}
