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

    public int getNumber() {
        return number;
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
                        while(isSharing) {
                            Object object = input.readObject();
                            System.out.println(((byte[])object).length);
                            if(object.equals(-2)) {
                                for(HashMap.Entry<Integer, Client> client : clients.entrySet()) {
                                    client.getValue().getOutput().writeObject(-2);
                                }
                                isSharing = false;
                            }
                            else {
                                for(HashMap.Entry<Integer, Client> client : clients.entrySet()) {
                                    client.getValue().getOutput().writeObject(object);
                                    System.out.println("Client #" + number + " sends client #" + client.getValue().getNumber());
                                }
                            }
                        }
                    }
                    else if(type == -3) {
                        int i = (int) input.readObject();
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + i);
                        server.getClients().get(i).getClients().put(number, this);
                        System.out.println(server.getClients().get(i).getClients().size());
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
