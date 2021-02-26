package services;

public class Stream implements Runnable {
    final private Connection con;
    final private Input in;

    @Override
    public void run() {
        con.open();
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
        try {
            con.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.yield();
    }

    public Stream(Connection con, boolean input) { // run as: push stream
        this.con = con;
        this.in = (input) ? new Input() : null;
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
                System.out.print(": ");
                System.out.println(query);
                return true;
        }
    }
}
