package serverPart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.Scanner;

public class Server implements Runnable {

    private final int port = 55000;

    private ServerSocket serverSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Scanner scanner;
    private int clientsCount = 0;

    private LinkedList<Client> clients = new LinkedList<>();

    @Override
    public void run() {
        System.out.println("Server was started");

        initialization();

        while(true) {
            try {
                Client client = new Client(serverSocket.accept(), clientsCount++);
                clients.add(client);
                new Thread(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initialization() {
        try {
            scanner = new Scanner(System.in);
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
