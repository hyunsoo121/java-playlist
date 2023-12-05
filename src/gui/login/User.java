package gui.login;

import java.util.Scanner;

public class User {
    private String phoneNum;
    private String id;
    private String name;

    public User() {}
    public User(String id, String name, String phoneNum) {
        setId(id);
        setName(name);
        setPhoneNum(phoneNum);
    }
    public User(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    @Override
    public boolean equals(Object o) {
        // 객체가 null이거나 User 클래스의 인스턴스가 아니면 false를 반환
        if(o == null || !(o instanceof User)) {
            return false;
        }
        User temp = (User)o;

        // 아이디 비교하여 동일 여부를 반환
        return id.equals(temp.getId());
    }

    public void read(Scanner scan) {
        phoneNum = scan.next();
        id = scan.next();
        name = scan.next();
    }
}
