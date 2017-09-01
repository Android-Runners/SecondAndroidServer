package mainPackage;

import serverPart.Server;

public class Dispatcher {

    public static void main(String[] args) {

        Server server = new Server();

        new Thread(server).start();
        new Thread(new AdminThread(server)).start();
    }
}
