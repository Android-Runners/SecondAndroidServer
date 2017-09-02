package serverPart;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server implements Runnable {

    private final int PORT = 53000;

    private ServerSocket serverSocket;
    private int clientsCount = 0;

    private LinkedList<Client> clients = new LinkedList<>();

    @Override
    public void run() {
        System.out.println("Server was started. Port: " + PORT);

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                Socket socket = serverSocket.accept();
                Client client = new Client(this, socket, clientsCount++);
                clients.add(client);
                new Thread(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToAllUsers(Message message, int fromClientNumber) {

        message.setIdSender(fromClientNumber);

        for(Client client : clients) {
            try {
                Socket socket = client.getSocket();
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                output.writeObject(message);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(Message message, int fromClientNumber) {
        try {
            message.setIdSender(fromClientNumber);

            if (message.getIdReceiver() == -1) {
                sendToAllUsers(message, fromClientNumber);
                return;
            }

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
