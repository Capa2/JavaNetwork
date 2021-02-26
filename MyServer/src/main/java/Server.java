import collections.*;
import services.*;

public class Server {
    final private ServerConnection con;
    final private Users users;

    public Server(int port) {
        con = new ServerConnection(port);
        users = new Users(true);
        con.open();
        systemLoop();
        con.close();
    }

    private void login(String username, String password) {
        try {
            con.push("Logging in");
            userSession(users.getValidUser(username, password));
        } catch (NullPointerException e) {
            con.push("Login failed");
        }
    }

    private void userSession(User user) {
        System.out.println("Login success");
        con.push("w:Velkommen " + user.getUsername());
        //con.push("t:" + user.getId());
    }

    private void systemLoop() {
            String line = "";
            while (!line.equals("quit")) {
                line = con.pull();
                System.out.println(": " + line);
                if (line.startsWith("login"))
                    login(line.split(" ")[1],
                          line.split(" ")[2]);
                else con.push("You said: " + line);
            }
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
    }
}