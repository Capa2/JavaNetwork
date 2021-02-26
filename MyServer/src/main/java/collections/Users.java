package collections;

import java.util.ArrayList;

public class Users {
    final private ArrayList<User> users;

    public Users(Boolean addSampleUsers) {
        users = new ArrayList<User>();
        if (addSampleUsers) addSampleUsers();
    }

    public void add(int id, String username, String password) {
        users.add(new User(id, username, password));
    }

    public Boolean validateUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username)
                    && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public User getValidUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void addSampleUsers() {
        add(1001, "johan", "jo");
        add(1002, "Donald", "do");
        add(1003, "Moe", "mo");
    }

    public User getUser(int id) {
        for (User u : users) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public User getUser(String username) {
        for (User u : users) {
            if (u.getUsername() == username) return u;
        }
        return null;
    }

}
