import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class PrintStreamDub extends FilterOutputStream implements Serializable {
    public PrintStreamDub(OutputStream out) {
        super(out);
    }
}
