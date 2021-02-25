import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
    private Socket socket;
    private ServerSocket server;
    private DataInputStream input;
    private ArrayList<User> users;
    private DataOutputStream output;

    public Server(int port) {
        users = new ArrayList<User>();
        startServer(port);
        users.add(new User(1420, "johan", "asd"));
        mainLoop();
        closeServer();
    }

    private void login(String login) {
        for (User u : users) {
            if (u.getUsername().equals(login.split(":")[0])
                    && u.getPassword().equals(login.split(":")[1])) {
                loginSucces(u);
                return;
            }
        }
        try {
            output.writeUTF("e:Login failed");
        } catch (IOException i) {
            i.printStackTrace();
            closeServer();
        }
    }

    private void loginSucces(User user) {
        System.out.println("Login success");
        try {
            output.writeUTF("w:Velkommen til systemet");
            output.writeUTF("t:" + String.valueOf(user.getId()));
        } catch (IOException i) {
            System.out.println(i);
            closeServer();
        }
    }

    private void mainLoop() {
        String line = "";
        while (!line.equals("quit")) {
            try {
                line = input.readUTF();
                System.out.println(line);
                output.writeUTF(".");
                if (line.startsWith("login")) {
                    login(line.split(" ")[1] + ":" + line.split(" ")[2]);
                }
                //if (line.trim().equals("are you here")) output.writeUTF("I'm listening...");
            } catch (IOException i) {
                System.out.println(i);
                closeServer();
            }
        }
    }

    private void startServer(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");
            input = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException i) {
            System.out.println(i);
            closeServer();
        }
    }

    private void closeServer() {
        try {
            System.out.println("Closing connection");
            socket.close();
            input.close();
            output.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(5000);
    }
}