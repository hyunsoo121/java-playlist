package manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public void read(T t){
        mList.add(t);
    }

    public void printAll() {
        for (T t : mList) {
            t.print();
        }
    }

    public void search() {
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

    public List<T> findAll(String kwd) {
        List<T> result = new ArrayList<>();
        for (T m : mList) {
            if (m.matches(kwd))
                result.add(m);
        }
        return result;
    }
}
