package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.lmpl.ProjectDAOImpl;
import dto.ProjectDTO;
import service.Impl.ProjectServiceImpl;
import service.ProjectService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ProjectServlet extends HttpServlet {

    private final ProjectService projectService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = Logger.getLogger(ProjectServlet.class.getName());

    public ProjectServlet() {
        this.projectService = new ProjectServiceImpl(new ProjectDAOImpl());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProjectDTO> projects = projectService.getAllProjects();
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("projects.jsp").forward(req, resp);
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
            String name = req.getParameter("name");
            String description = req.getParameter("description");

            if (name != null && description != null) {
                ProjectDTO project = new ProjectDTO();
                project.setName(name);
                project.setDescription(description);
                projectService.createProject(project);
                resp.sendRedirect(req.getContextPath() + "/ProjectServlet");
            } else {
                logger.warning("Empty request body received in doPost");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        if (idStr != null && name != null && description != null) {
            Long id = Long.valueOf(idStr);
            ProjectDTO project = new ProjectDTO(id, name, description, null);
            projectService.updateProject(project);
            resp.sendRedirect(req.getContextPath() + "/ProjectServlet");
        } else {
            logger.warning("Empty request body received in doPut");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            Long projectId = Long.valueOf(idStr);
            projectService.deleteProject(projectId);
            resp.sendRedirect(req.getContextPath() + "/ProjectServlet");
        } else {
            logger.warning("No project ID provided for deletion");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No project ID provided");
        }
    }
}
