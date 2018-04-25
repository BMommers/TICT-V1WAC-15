package nl.hu.v1wac.firstapp.servlets;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = "/CalcServlet.do")
public class CalcServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer n1 = Integer.parseInt(req.getParameter("number1"));
        Integer n2 = Integer.parseInt(req.getParameter("number2"));
        String op = req.getParameter("operator");
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println(" <title>Dynamic Example</title>");
        out.println(" <body>");
        out.println(" <h2>" + n1 + " " + op + " " + n2 + " = ... </h2>");
        switch (op) {
            case "+":
                out.println(" <h2>" + (n1 + n2) + "</h2>");
                break;
            case "-":
                out.println(" <h2>" + (n1 - n2) + "</h2>");
                break;
            case "/":
                out.println(" <h2>" + (n1 / n2) + "</h2>");
                break;
            case "*":
                out.println(" <h2>" + (n1 * n2) + "</h2>");
                break;
        }
        out.println(" </body>");
        out.println("</html>");
    }
}