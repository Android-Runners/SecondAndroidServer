import java.util.Arrays;

public class Dispatcher {

    public static void main(String[] args) {

        Server server = new Server();

        new Thread(server).start();
    }
}
