package serverPart;

public class Message {

    public Object getData() {
        return data;
    }

    private Object data;

    public int getIdReceiver() {
        return idReceiver;
    }

    private int idReceiver;

    public Message(Object data, int idReceiver) {
        this.data = data;
        this.idReceiver = idReceiver;
    }
}
