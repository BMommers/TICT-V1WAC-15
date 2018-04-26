package nl.hu.v1wac.firstapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@WebServlet(urlPatterns = "/landingpage")
public class LandingPage extends HttpServlet {

    private static final String htmlTemplate = "<!DOCTYPE html><html><title>LandingPage 1.0</title><body>%s</body></html>";
    private static final String dirTemplate = "<label><a href=\"%s\">%s</a></label><br/>";
    private static final Path resourceBase = Paths.get("D:\\Librarys\\intelliJ-workspace\\TICT-V1WAC-15\\src\\main\\webapp");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathParam = request.getParameter("path");
        String decodedParam = (pathParam == null ? "" : URLDecoder.decode(pathParam, "UTF-8"));
        Path requestedPath = Paths.get(resourceBase + decodedParam);
        if (Files.isDirectory(requestedPath)) {
            requestedPath = requestedPath.toRealPath();
            StringBuilder responseBuilder = new StringBuilder();
            for (File dir : requestedPath.toFile().listFiles(file -> file.isDirectory())) {
                if (dir.getName() != "WEB-INF") {
                    responseBuilder.append(String.format(dirTemplate, dir.getName(), dir.getName()));
                }
            }
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.write(String.format(htmlTemplate, responseBuilder.toString()));
        }
        else if (Files.isRegularFile(requestedPath)) {
            System.out.println("FILE GEVONDEN");
            requestedPath = requestedPath.toRealPath();

        }
    }
}