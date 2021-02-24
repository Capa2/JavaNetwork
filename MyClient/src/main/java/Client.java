import java.net.*;
import java.io.*;

// NOTE: the 'output' variable is called 'out' in the original code

public class Client {
    private Socket socket = null;
    private DataInputStream input = null;
    // add INPUT stream to handle server replies:
    private DataInputStream replies = null;
    private DataOutputStream output = null;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            input = new DataInputStream(System.in);
            // initialise server reply INPUT stream:
            replies = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
        String line = "";
        // add String to store server replies:
        String reply = "";
        while (!line.equals("Over")) {
            try {
                line = input.readLine();
                output.writeUTF(line);
                // continuously read server replies in main loop:
                reply = replies.readUTF();
                System.out.println(reply);
            } catch (IOException i) {
                System.out.println(i);
            }
        }
        try {
            input.close();
            // Close the new data stream:
            replies.close();
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