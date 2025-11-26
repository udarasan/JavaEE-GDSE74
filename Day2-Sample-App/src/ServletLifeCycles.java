import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/slc")
public class ServletLifeCycles extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("ServletLifeCycles init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ServletLifeCycles doGet");
    }

    @Override
    public void destroy() {
        System.out.println("ServletLifeCycles destroy");
    }
}
