package week1.les1.practicum2;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.util.Scanner;

class Server {
    public static void main(String[] arg) throws Exception {
        ServerSocket ss = new ServerSocket(4711);
        Socket s = ss.accept();
        InputStream is = s.getInputStream();

        Scanner sc = new Scanner(is);

        while (sc.hasNextLine()) {
            System.out.print(sc.nextLine());
        }

        s.close();
        ss.close();
    }
}
