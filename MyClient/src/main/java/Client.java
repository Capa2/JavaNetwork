import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner userInput;
    private DataInputStream serverResponse;
    private DataOutputStream output;

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
                line = userInput.nextLine();
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
            serverResponse = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
            userInput = new Scanner(new BufferedInputStream(System.in));
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
            userInput.close();
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
        char type = (response.contains(":")) ? response.charAt(0) : 'x';
        switch (type) {
            case 'w':
                try {
                    responseHandler(serverResponse.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case 't':
                System.out.println("Token: " + query);
            case 'x':
                System.out.println("- " + query);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 5000);
    }
}