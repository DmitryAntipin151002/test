package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.lmpl.UserDAOImpl;
import dto.UserDTO;
import service.Impl.UserServiceImpl;
import service.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class UserServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = Logger.getLogger(UserServlet.class.getName());

    public UserServlet(UserService userService) {
        this.userService = new UserServiceImpl(new UserDAOImpl());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDTO> users = userService.getAllUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        if (method != null) {
            switch (method) {
                case "PUT":
                    doPut(req, resp);
                    break;
                case "DELETE":
                    doDelete(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid method");
            }
        } else {
            String email = req.getParameter("email");
            String passwordHash = req.getParameter("passwordHash");

            if (email != null && passwordHash != null) {
                UserDTO user = new UserDTO();
                user.setEmail(email);
                user.setPasswordHash(passwordHash);
                userService.createUser(user);
                resp.sendRedirect(req.getContextPath() + "/UserServlet");
            } else {
                logger.warning("Empty request body received in doPost");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String email = req.getParameter("email");
        String passwordHash = req.getParameter("passwordHash");

        if (idStr != null && email != null && passwordHash != null) {
            Long id = Long.valueOf(idStr);
            UserDTO user = new UserDTO(id, email, passwordHash);
            userService.updateUser(user);
            resp.sendRedirect(req.getContextPath() + "/UserServlet");
        } else {
            logger.warning("Empty request body received in doPut");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            Long userId = Long.valueOf(idStr);
            userService.deleteUser(userId);
            resp.sendRedirect(req.getContextPath() + "/UserServlet");
        } else {
            logger.warning("No user ID provided for deletion");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No user ID provided");
        }
    }
}
