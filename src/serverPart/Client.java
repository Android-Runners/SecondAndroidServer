package serverPart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private int number;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Client(Socket socket, int number) {
        this.number = number;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            output = (ObjectOutputStream) socket.getOutputStream();
            input = (ObjectInputStream) socket.getInputStream();

            System.out.println("Client #" + number + " was connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
