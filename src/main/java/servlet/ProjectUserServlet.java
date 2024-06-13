package servlet;

import dao.ProjectUserDAO;
import dao.lmpl.ProjectUserDAOImpl;
import dto.ProjectUserDTO;
import service.Impl.ProjectUserServiceImpl;
import service.ProjectUserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ProjectUserServlet extends HttpServlet {

    private final ProjectUserService projectUserService;
    private static final Logger logger = Logger.getLogger(ProjectUserServlet.class.getName());

    public ProjectUserServlet() {
        this.projectUserService = new ProjectUserServiceImpl(new ProjectUserDAOImpl());
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProjectUserDTO> projectUsers = projectUserService.getAllProjectUsers();
        req.setAttribute("projectUsers", projectUsers);
        req.getRequestDispatcher("projectUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        if (method != null) {
            switch (method) {
                case "DELETE":
                    doDelete(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid method");
            }
        } else {
            String projectIdStr = req.getParameter("projectId");
            String userIdStr = req.getParameter("userId");

            if (projectIdStr != null && userIdStr != null) {
                Long projectId = Long.valueOf(projectIdStr);
                Long userId = Long.valueOf(userIdStr);
                ProjectUserDTO projectUser = new ProjectUserDTO(projectId, userId);
                projectUserService.assignUserToProject(projectUser);
                resp.sendRedirect(req.getContextPath() + "/ProjectUserServlet");
            } else {
                logger.warning("Empty request body received in doPost");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectIdStr = req.getParameter("projectId");
        String userIdStr = req.getParameter("userId");
        if (projectIdStr != null && userIdStr != null) {
            Long projectId = Long.valueOf(projectIdStr);
            Long userId = Long.valueOf(userIdStr);
            ProjectUserDTO projectUser = new ProjectUserDTO(projectId, userId);
            projectUserService.unassignUserFromProject(projectUser);
            resp.sendRedirect(req.getContextPath() + "/ProjectUserServlet");
        } else {
            logger.warning("No project ID and/or user ID provided for deletion");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No project ID and/or user ID provided");
        }
    }
}
