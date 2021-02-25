package services;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
    ServerSocket outputSocket;
    Socket inputSocket;
    DataInputStream input;
    DataOutputStream output;
    int port;
    public Connection(int port) {
        this.port = port;
    }

    public void open() {
        try {
            outputSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            inputSocket = outputSocket.accept();
            System.out.println("Client accepted");
            input = new DataInputStream(new BufferedInputStream(inputSocket.getInputStream()));
            output = new DataOutputStream(inputSocket.getOutputStream());
        } catch (IOException i) {
            System.out.println(i);
            close();
        }
    }
    public void close() {
        try {
            System.out.println("Closing connection");
            inputSocket.close();
            input.close();
            output.close();
        } catch (IOException i) {
            System.out.println(i);
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
