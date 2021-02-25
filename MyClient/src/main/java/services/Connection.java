package services;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
    Socket inputSocket;
    DataInputStream input;
    DataOutputStream output;
    String address;
    int port;
    public Connection(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void open() {
        try {
            inputSocket = new Socket(address, port);
            System.out.println("Connected");
            input = new DataInputStream(
                    new BufferedInputStream(inputSocket.getInputStream()));
            output = new DataOutputStream(inputSocket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
            close();
        } catch (IOException i) {
            System.out.println(i);
            close();
        }
    }
    public void close() {
        try {
            input.close();
            output.close();
            inputSocket.close();
        } catch (IOException i) {
            System.out.println(i);
            close();
        }
    }

    public void push(String s) {
        try {
            output.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String pull() {
        try {
            return input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
