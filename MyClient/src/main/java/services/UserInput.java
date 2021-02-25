package services;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class UserInput {
    Scanner userInput;

    public UserInput() {
        userInput = new Scanner(new BufferedInputStream(System.in));
    }

    public String nextLine() {
        return userInput.nextLine();
    }

    public void close() {
        userInput.close();
    }
}
