

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class BitmapReaderWriter {

    private byte[] byteArray;
    private Object object;

    public Integer getToWhom() {
        return toWhom;
    }

    private Integer toWhom;
    private Server server;

    public BitmapReaderWriter(Server server) {
        this.server = server;
    }

    public BitmapReaderWriter() {}

    private byte[] remove(byte[] bytes, int index) {
        if (index >= 0 && index < bytes.length) {
            byte[] copy = new byte[bytes.length-1];
            System.arraycopy(bytes, 0, copy, 0, index);
            System.arraycopy(bytes, index+1, copy, index, bytes.length-index-1);
            return copy;
        }
        return bytes;
    }

    public void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        object = in.readObject();
        toWhom = Integer.valueOf(((byte[])object)[0]);
        byte[] receiveData = remove((byte[])object, 0);
        object = receiveData;
    }

    public void writeObject() throws IOException {
        ObjectOutputStream objectOutputStream = server.getClients().get(toWhom).getOutput();
        objectOutputStream.writeObject(object);
    }
}
