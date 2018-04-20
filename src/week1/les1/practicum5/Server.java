package week1.les1.practicum5;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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