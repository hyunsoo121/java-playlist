package manager;

import java.util.Scanner;

public interface Manageable {
    void read(Scanner sc);

    void print();

    boolean matches(String kwd);

    String getImagePath();
}
