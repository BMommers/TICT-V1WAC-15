package nl.hu.v1wac.firstapp.servlets;

import java.io.*; import javax.servlet.http.*; import javax.servlet.ServletException;
import java.net.*; import java.nio.file.*; import javax.servlet.annotation.WebServlet;


@WebServlet(urlPatterns = "/filebrowser")
public class FileBrowserServlet extends HttpServlet {

    private static final String htmlTemplate = "<!DOCTYPE html><html><title>FileBrowser 1.0</title><body>%s</body></html>";
    private static final String dirTemplate = "<label><a href=\"?path=%s\">%s</a></label><br/>";
//    private static final String fileTemplate = "<label>%s</label><br/>";
    private static final String fileTemplate = "<label><a href=\"?path=%s\">%s</a></label><br/>";
    private static final Path resourceBase = Paths.get("d:/librarys/Documents/");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathParam = request.getParameter("path");
        String decodedParam = (pathParam == null ? "" : URLDecoder.decode(pathParam, "UTF-8"));
        Path requestedPath = Paths.get(resourceBase + decodedParam);
        if (Files.isDirectory(requestedPath)) {
            requestedPath = requestedPath.toRealPath();
            StringBuilder responseBuilder = new StringBuilder();
            for (File dir : requestedPath.toFile().listFiles(file -> file.isDirectory())) {
                String encodedPath = URLEncoder.encode(decodedParam + "/" + dir.getName(), "UTF-8");
                responseBuilder.append(String.format(dirTemplate, encodedPath, dir.getName()));
            }
            for (File file : requestedPath.toFile().listFiles(file -> file.isFile())) {
                String encodedPath = URLEncoder.encode(decodedParam + "/" + file.getName(), "UTF-8");
                responseBuilder.append(String.format(fileTemplate, encodedPath, file.getName()));
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