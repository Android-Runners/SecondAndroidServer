import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

    private boolean isSharing = false;

    public HashMap<Integer, Client> getClients() {
        return clients;
    }

    private HashMap<Integer, Client> clients = new HashMap<>();

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
                    int type = (int) input.readObject();
                    if(type == -1) {
                        isSharing = true;
                        Object object = input.readObject();
                        if(object.equals(-2)) {
                            for(HashMap.Entry<Integer, Client> client : clients.entrySet()) {
                                client.getValue().getOutput().writeObject(-2);
                            }
                        }
                        else {
                            for(HashMap.Entry<Integer, Client> client : clients.entrySet()) {
                                client.getValue().getOutput().writeObject(object);
                            }
                        }
                    }
                    else if(type == -3) {
                        int i = (int) input.readObject();
                        server.getClients().get(i).getClients().put(i, this);
                    }
                    else if(type == -4) {
                        int i = (int) input.readObject();
                        server.getClients().get(i).getClients().remove(i);
                    }
//                    bitmapReaderWriter = new BitmapReaderWriter(server, number);
//                    bitmapReaderWriter.readObject(input);
//                    System.out.println("Received");
//                    System.out.println("Client #" + number + " sends to " + bitmapReaderWriter.getToWhom() + " video: " + bitmapReaderWriter.getSize());
//                    bitmapReaderWriter.writeObject();

                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
