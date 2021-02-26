package services;

public class Stream implements Runnable {
    final private Connection con;
    final private Input in;

    @Override
    public void run() {
        while (in != null) {
            String line = in.nextLine();
            if (line.equals("quit")) break;
            con.push(line);
        }
        while (in == null) {
            try {
                if (!pullHandler(con.pull())) break;
            } catch (NullPointerException e) {break;}
        }
        Thread.yield();
    }

    public Stream(Connection con, Input in) { // run as: push stream
        this.con = con;
        this.in = in;
    }
    public Stream(Connection con) { // run as: pull stream
        this.con = con;
        this.in = null;
    }

    private boolean pullHandler(String pull) {
        String query = (pull.contains(":")) ? pull.split(":")[1] : pull;
        char type = (pull.contains(":")) ? pull.charAt(0) : null;
        switch (type) {
            case 't':
                System.out.println("Token: " + query);
                return true;
            case 'q':
                System.out.println(query);
                return false;
            default:
                System.out.println(query);
                return true;
        }
    }
}
