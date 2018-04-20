package week1.les1.practicum6;

import java.net.ServerSocket;
import java.net.Socket;

class Server {
    public static void main(String[] arg) throws Exception {
        ServerSocket ss = new ServerSocket(4711);
        while (true) {
            Socket s = ss.accept();
            MyServlet clientThread = new MyServlet(s);
            clientThread.start();

        }
    }
}