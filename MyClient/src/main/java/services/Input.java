package services;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Input implements Runnable {
    Scanner input;

    public Input() {
        input = new Scanner(new BufferedInputStream(System.in));
    }

    public String nextLine() {
        return input.nextLine();
    }

    public void close() {
        input.close();
        Thread.yield();
    }
    public void run() {
    }
}
