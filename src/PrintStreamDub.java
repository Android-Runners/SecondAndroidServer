import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;

public class PrintStreamDub extends PrintStream implements Serializable {
    public PrintStreamDub(OutputStream out) {
        super(out);
    }
}
