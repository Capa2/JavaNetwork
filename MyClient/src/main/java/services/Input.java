package services;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Input {
    Scanner input;

    public Input() {
        input = new Scanner(new BufferedInputStream(System.in));
    }

    public String nextLine() {
        return input.nextLine();
    }

    public void close() {
        input.close();
    }
}
