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
           // System.out.println("34");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            System.out.println("Client #" + number + " was connected");
            BitmapReaderWriter bitmapReaderWriter = new BitmapReaderWriter(server, number);
            bitmapReaderWriter.setObject(number);
            bitmapReaderWriter.setToWhom(number);
            bitmapReaderWriter.writeObject();
           while(true) {
                try {
                    bitmapReaderWriter = new BitmapReaderWriter(server, number);
                    bitmapReaderWriter.readObject(input);
                    System.out.println("Received");
                    System.out.println("Client #" + number + " sends to " + bitmapReaderWriter.getToWhom() + " video: " + bitmapReaderWriter.getSize());
                    bitmapReaderWriter.writeObject();
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
