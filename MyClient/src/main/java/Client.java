import java.net.*;
import java.io.*;

public class Client {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataInputStream serverResponse = null;
    private DataOutputStream output = null;

    public Client(String address, int port) {
        startClient(address, port);
        mainLoop();
        closeClient();
    }

    public void mainLoop() {
        String line = "";
        while (!line.equals("quit")) {
            try {
                // input
                line = input.readLine();
                output.writeUTF(line);
                // response
                responseHandler(serverResponse.readUTF());
            } catch (IOException i) {
                System.out.println(i);
                closeClient();
            }
        }
    }

    public void startClient(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            input = new DataInputStream(System.in);
            serverResponse = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
            closeClient();
        } catch (IOException i) {
            System.out.println(i);
            closeClient();
        }
    }

    public void closeClient() {
        try {
            input.close();
            serverResponse.close();
            output.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
            closeClient();
        }
    }

    private void responseHandler(String response) {
        String query = (response.contains(":")) ? response.split(":")[1] : response;
        char type = (response.contains(":")) ? response.charAt(0) : 'n';
        switch (type) {
            case 'w':
                System.out.println(query);
                try {
                    responseHandler(serverResponse.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 't':
                System.out.println("Token: " + query);
                break;
            default:
                System.out.println(query);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 5000);
    }
}