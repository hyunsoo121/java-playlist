package gui.login;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDataSet {
    ArrayList<User> userList = new ArrayList<User>();

    //파일읽기
    public void readAll(String filename) {
        Scanner filein = openFile(filename);
        User u = null;
        while (filein.hasNext()) {
            u = new User();
            u.read(filein);
            userList.add(u);
        }
        filein.close();
    }

    Scanner openFile(String filename) {
        Scanner filein = null;
        try {
            filein = new Scanner(new File(filename));

        } catch (IOException e) {
            System.out.println("파일 오픈 실패 " + filename);
            System.exit(0);
        }
        return filein;
    }

    // 회원 추가
    public void addUsers(User user) throws IOException {
        userList.add(user);
//        fileWritter(user);
    }
    //user.txt 파일에 새로운 회원 입력
    public void fileWritter(User user) throws IOException {
        FileWriter writer = new FileWriter("user.txt",true);
        writer.write("\r\n"+user.getPhoneNum()+" ");
        writer.write(user.getId()+" ");
        writer.write(user.getName());
        writer.close();
    }
    // 아이디 중복 확인
    public boolean isIdOverlap(String id) {
        return userList.contains(new User(id));
    }
    // 유저 정보 가져오기
    public User getUser(String id) {
        return userList.get(userList.indexOf(new User(id)));
    }
    // 회원인지 아닌지 확인
    public boolean contains(User user) {
        return userList.contains(user);
    }
}
