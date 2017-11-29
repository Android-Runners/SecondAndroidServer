

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class BitmapReaderWriter {

    private byte[] byteArray;

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    private Object object;

    public Integer getSize() {
        return size;
    }

    private Integer size;

    public Integer getToWhom() {
        return toWhom;
    }

    public void setToWhom(Integer toWhom) {
        this.toWhom = toWhom;
    }

    private Integer toWhom;
    private Server server;
    private Integer number;

    public BitmapReaderWriter(Server server, Integer number) {

        this.server = server;
        this.number = number;
    }

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
        size = ((byte[])object).length;
        toWhom = (int) ((byte[]) object)[0];
        object = remove((byte[])object, 0);
    }

    private byte[] concat(byte[] a, byte[] b) {
        byte[] t = new byte[a.length + b.length];
        System.arraycopy(a, 0, t, 0, a.length);
        System.arraycopy(b, 0, t, a.length, b.length);
        return t;
    }

    public void writeObject() throws IOException {
        ObjectOutputStream objectOutputStream;
      //  try {
        objectOutputStream = server.getClients().get(toWhom).getOutput();
        objectOutputStream.flush();
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
       /* }catch(IndexOutOfBoundsException e){
            String s = new String("Такого користувача не зареєстровано на сервері");
            object = s.getBytes();
            objectOutputStream = server.getClients().get(number).getOutput();
            objectOutputStream.writeObject(concat(new byte[]{-1}, (byte[])object));
            System.out.println(e.getMessage() + "!!");
        }*/
    }
}
