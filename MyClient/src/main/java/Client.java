import services.ClientConnection;
import services.Input;
import services.Stream;

public class Client {
    volatile private ClientConnection con;
    volatile private Input userInput;

    public Client(String address, int port) {
        con = new ClientConnection(address, port);
        userInput = new Input();
        Thread connectionThread = new Thread(con);
        Thread inStream = new Thread(new Stream(con));
        Thread outStream = new Thread(new Stream(con, userInput));
        connectionThread.start();
        try {
            connectionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inStream.start();
        outStream.start();

    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 5000);
    }
}