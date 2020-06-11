package autoupdate;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns={"/update"})
public class UpdateApi extends HttpServlet 
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                        throws ServletException, IOException 
    {
        UpdateHardwareList updateHardwareList = new UpdateHardwareList();

        UpdateMongo updateMongo = new UpdateMongo();

        updateHardwareList.update();

        updateMongo.updateMongo(updateHardwareList);
    }

    // @Override
    // protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    //                     throws ServletException, IOException 
    // {
    //     resp.getWriter().println("Use Post to update data...");
    // }
}