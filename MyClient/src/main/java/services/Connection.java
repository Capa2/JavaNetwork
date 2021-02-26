package services;

public interface Connection {
    void open();
    void close();
    void push(String string);
    String pull();
}
