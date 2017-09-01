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
}
