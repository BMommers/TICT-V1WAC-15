package week1.les1.practicum2;

import java.net.Socket;
import java.io.OutputStream;
import java.io.PrintWriter;

class Client {
    public static void main(String[] arg) throws Exception {
        Socket s = new Socket("145.89.78.137", 4711);
        OutputStream os = s.getOutputStream();
        PrintWriter pr = new PrintWriter(os);

        pr.write("BIER");
        pr.flush();
        s.close();
    }
}
