package week1.les1.practicum5;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.util.Scanner;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;

public class MyServlet extends Thread {
    private Socket socket;

    public MyServlet(Socket sock){
        socket = sock;

    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            PrintWriter pw = new PrintWriter(os);

            Scanner sc = new Scanner(is);
            String recv = sc.nextLine();

            while (!recv.isEmpty()) {
                System.out.println(recv);
                recv = sc.nextLine();
            }

            pw.write("HTTP/1.1 200 OK\n");
            pw.write("\r\n");
            pw.write("<h1>BIER</h1>");
            pw.write("<img src='https://www.dokterdokter.nl/wp-content/uploads/2014/04/23042932_m.jpg' />\n");
            pw.flush();

            socket.close();
        }
        catch (Exception E) {}
    }
}
