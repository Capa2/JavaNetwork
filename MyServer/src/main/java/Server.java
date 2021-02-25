import collections.*;
import services.*;

import java.util.ArrayList;

public class Server {
    private Connection con;
    private Users users;

    public Server(int port) {
        con = new Connection(port);
        users = new Users(true);
        con.open();
        mainLoop();
        con.close();
    }

    private void login(String username, String password) {
        try {
            userSession(users.getValidUser(username, password));
        } catch (NullPointerException e) {
            con.push("Login failed");
        }
    }

    private void userSession(User user) {
        System.out.println("Login success");
        con.push("w:Velkommen til systemet");
        con.push("t:" + String.valueOf(user.getId()));
    }

    private void mainLoop() {
        String line = "";
        while (!line.equals("quit")) {
            line = con.pull();
            System.out.println(line);
            con.push(".");
            if (line.startsWith("login")) {
                login(line.split(" ")[1], line.split(" ")[2]);
            }
        }
    }

    public static void main(String args[]) {
        Server server = new Server(5000);
    }
}