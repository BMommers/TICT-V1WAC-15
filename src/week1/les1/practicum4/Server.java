package week1.les1.practicum4;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.util.Scanner;
import java.io.OutputStream;
import java.io.PrintWriter;

class Server {
    public static void main(String[] arg) throws Exception {
            ServerSocket ss = new ServerSocket(4711);
            Socket s = ss.accept();

            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
            PrintWriter pr = new PrintWriter(os);

            Scanner sc = new Scanner(is);

            String recv = sc.nextLine();
            while (!recv.isEmpty()) {
                System.out.println(recv);
                recv = sc.nextLine();
            }

            pr.write("HTTP/1.1 200 OK\n");
            pr.write("\r\n");
            pr.write("<h1>Tekst</h1>\n");
            pr.flush();

            s.close();
            ss.close();
    }
}

