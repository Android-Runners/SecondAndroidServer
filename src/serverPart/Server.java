package serverPart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
        System.out.println("Server was started. Port: " + port);

        initialization();

        while(true) {
            try {
                Socket socket = serverSocket.accept();
                Client client = new Client(this, socket, clientsCount++);
                clients.add(client);
                new Thread(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public void sendToAllUsers(Message message) {

        message.setIdSender(-1);

        for(Client client : clients) {
            try {
                Socket socket = client.getSocket();
                ObjectOutputStream output = (ObjectOutputStream) socket.getOutputStream();
                output.flush();
                output.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(Message message, int fromClientNumber) {
        try {
            message.setIdSender(fromClientNumber);

            ObjectOutputStream output = (ObjectOutputStream) clients.get(fromClientNumber).getSocket().getOutputStream();
            output.flush();
            output.writeObject(message);
            output.flush();

            System.out.println("Client #" + fromClientNumber + " sent client #" + message.getIdReceiver() + " " + message.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
