import java.net.*;
import java.io.*;

public class Client {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataInputStream replies = null;
    private DataOutputStream output = null;

    // constructor to put ip address and port
    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            input = new DataInputStream(System.in);
            replies = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
        String line = "";
        String reply = "";
        while (!line.equals("Over")) {
            try {
                line = input.readLine();
                output.writeUTF(line);
                reply = replies.readUTF();
                System.out.println(reply);
            } catch (IOException i) {
                System.out.println(i);
            }
        }
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 5000);
    }
}