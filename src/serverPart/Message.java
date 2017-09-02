package serverPart;

import java.io.Serializable;

public class Message implements Serializable {

    public Object getData() {
        return data;
    }

    private Object data;

    public int getIdReceiver() {
        return idReceiver;
    }

    private int idReceiver;

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    private int idSender;

    public Message(Object data, int idReceiver) {
        this.data = data;
        this.idReceiver = idReceiver;
    }

    @Override
    public String toString() {
        return "Data: " + ((Integer) data);
    }
}
