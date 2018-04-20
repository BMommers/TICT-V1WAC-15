package week1.les1.practicum3;

import java.net.Socket;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

class Client {
    public static void main(String[] arg) throws Exception {
        Socket s = new Socket("145.89.78.137", 4711);
        OutputStream os = s.getOutputStream();
        PrintWriter pr = new PrintWriter(os);
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Voer in:");
        String invoer = keyboard.nextLine();

        while (!invoer.equals("stop")) {
            pr.write(invoer);
            pr.flush();

            System.out.println("Voer in:");
            invoer = keyboard.nextLine();
        }

        s.close();
    }
}
