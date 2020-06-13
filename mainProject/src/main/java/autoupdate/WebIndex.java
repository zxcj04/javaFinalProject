package autoupdate;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns={"/"})
public class WebIndex extends HttpServlet 
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                        throws ServletException, IOException 
    {
        resp.getWriter().println("<h1>This is the update-only api of GeeGee</h1>");
    }
}