import services.ClientConnection;
import services.Input;
import services.Stream;

public class Client {

    public Client(String address, int port) {
        Thread inStream = new Thread(new Stream(new ClientConnection(address, port), true));
        Thread outStream = new Thread(new Stream(new ClientConnection(address, port), false));
        inStream.start();
        outStream.start();
        try {
            inStream.join();
            outStream.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 5000);
    }
}