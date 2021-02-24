import java.net.*;
import java.io.*;

// NOTE: the 'input' variable is called 'in' in the original code

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream input = null;
    // add output stream to reply to client:
    private DataOutputStream output = null;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");
            input = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            // initialise server reply OUTPUT stream:
            output = new DataOutputStream(socket.getOutputStream());
            String line = "";
            while (!line.equals("Over")) {
                try {
                    line = input.readUTF();
                    System.out.println(line);
                    // check client message, and reply if criteria are met
                    if (line.trim().equals("are you here")) {
                        output.writeUTF("I'm listening...");
                    }
                } catch (IOException i) {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");
            socket.close();
            input.close();
            // Close the new data stream:
            output.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(5000);
    }
}