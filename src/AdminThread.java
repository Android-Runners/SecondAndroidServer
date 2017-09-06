import com.savelyevlad.secondclientandroid.ClientServerCommonClasses.Message;

import java.util.Scanner;

public class AdminThread implements Runnable {

    private Server server;
    private Scanner scanner = new Scanner(System.in);

    AdminThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        while(true) {
            String s = scanner.nextLine();

            Message message = new Message(s, -1);

            server.sendToAllUsers(message, -1);

            System.out.println("Done");
        }
    }
}
