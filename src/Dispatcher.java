import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Random;

public class Dispatcher {

    public static void main(String[] args) throws Exception {
        new Thread(new Server()).start();
    }

}