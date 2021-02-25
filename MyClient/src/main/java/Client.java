import services.Connection;
import services.UserInput;

public class Client {
    private Connection con;
    private UserInput userInput;

    public Client(String address, int port) {
        userInput = new UserInput();
        con = new Connection(address, port);
        con.open();
        mainLoop();
        con.close();
    }

    public void mainLoop() {
        String line = "";
        while (!line.equals("quit")) {
            line = userInput.nextLine();
            con.push(line);
            responseHandler(con.pull());
        }
    }

    private void responseHandler(String response) {
        String query = (response.contains(":")) ? response.split(":")[1] : response;
        char type = (response.contains(":")) ? response.charAt(0) : 'x';
        switch (type) {
            case 'w':
                responseHandler(con.pull());
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