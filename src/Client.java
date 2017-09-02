import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Server server;
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    private int number;

    public ObjectOutputStream getOutput() {
        return output;
    }

    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Client(Server server, Socket socket, int number) {
        this.number = number;
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            System.out.println("Client #" + number + " was connected");

            while(true) {
                try {
                    Message message = (Message) input.readObject();

                    if(message.getData() instanceof PrintStreamDub) {
                        ((PrintStreamDub) message.getData()).write(55);
                    }

//                    server.sendMessage(message, number);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
