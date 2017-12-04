import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

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

    int max = 0;

    public HashMap<Integer, Client> getClients() {
        return clients;
    }

    private HashMap<Integer, Client> clients = new HashMap<>();

    private Integer host;

    private boolean isWant = true;
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
                    int type = -4;
                    try {
                        type = (int) input.readObject();
                    }catch(EOFException eoef) {
                        server.getClientsTrans().get(host).clients.remove(number);
                        return;
                    }
                    if(type == -1) {
                        server.getClientsTrans().add(this);
                        isSharing = true;
                        while(isSharing) {
                            Object object = null;
                            try {
                                object = input.readObject();
                            }catch(EOFException ioe){
                                server.getClientsTrans().remove(this);
                                System.out.println("TRANS\n"+ioe.getMessage());
                                return;
                            }
                            max =  Math.max(max, ((byte[])object).length);
                            if(object.equals(-2)) {
                                synchronized (this) {
                                    for(HashMap.Entry<Integer, Client> client : clients.entrySet()) {
                                        client.getValue().getOutput().writeObject(-2);
                                    }
                                    isSharing = false;
                                }
                            }
                            else {
                                for(HashMap.Entry<Integer, Client> client : clients.entrySet()) {
                                    try {
                                        client.getValue().getOutput().writeObject(object);
                                    } catch(Exception e) { }
                                }
                            }
                        }
                    }
                    else if(type == -3) {
                        synchronized (this) {
                            if(host != null) {
                                server.getClientsTrans().get(host).clients.remove(number);
                            }
                            int i = (int) input.readObject();
                            host = i;
                            server.getClients().get(i).getClients().put(number, this);
                            System.out.println(i);
                        }
                    }
                    else if(type == -4) {
                        int i = (int) input.readObject();
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + i + " " + number);
                        server.getClients().get(i).getClients().remove(number);
                    }
                    else if(type == -5) {
                        byte[] trans = new byte[server.getClientsTrans().size()];
                        int i = 0;
                        for(Client temp : server.getClientsTrans()){
                            trans[i] = (byte)temp.number;
                            i++;
                        }
                        output.flush();
                        output.writeObject(concat(new byte[]{63, 25, 10, 31}, trans));
                        System.out.println(Arrays.toString(trans));
                    }
                } catch (Exception e) {
                  //  e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private byte[] concat(byte[] a, byte[] b) {
        byte[] t = new byte[a.length + b.length];
        System.arraycopy(a, 0, t, 0, a.length);
        System.arraycopy(b, 0, t, a.length, b.length);
        return t;
    }
}
