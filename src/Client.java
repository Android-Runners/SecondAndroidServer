import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private Server server;

    public Socket getSocket() {
        return socket;
    }

    private Socket socket;

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
            System.out.println("34");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            System.out.println("Client #" + number + " was connected");

           while(true) {
                try {
                    BitmapReaderWriter bitmapReaderWriter = new BitmapReaderWriter(server);
                    bitmapReaderWriter.readObject(input);
                    System.out.println("Received");
                    bitmapReaderWriter.writeObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
