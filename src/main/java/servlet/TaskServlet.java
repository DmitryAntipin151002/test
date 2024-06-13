package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.lmpl.TaskDAOImpl;
import dto.TaskDTO;
import service.Impl.TaskServiceImpl;
import service.TaskService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class TaskServlet extends HttpServlet {

    private final TaskService taskService;
    private final ObjectMapper objectMapper;
    private static final Logger logger = Logger.getLogger(TaskServlet.class.getName());

    public TaskServlet(TaskService taskService) {
        this.taskService = taskService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskDTO> tasks = taskService.getAllTasks();
        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher("tasks.jsp").forward(req, resp);
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
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String userIdStr = req.getParameter("userId");

            if (title != null && description != null && userIdStr != null) {
                TaskDTO task = new TaskDTO();
                task.setTitle(title);
                task.setDescription(description);
                task.setUserId(Long.parseLong(userIdStr));
                taskService.createTask(task);
                resp.sendRedirect(req.getContextPath() + "/TaskServlet");
            } else {
                logger.warning("Empty request body received in doPost");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String userIdStr = req.getParameter("userId");

        if (idStr != null && title != null && description != null && userIdStr != null) {
            Long id = Long.valueOf(idStr);
            TaskDTO task = new TaskDTO(id, title, description, Long.parseLong(userIdStr));
            taskService.updateTask(task);
            resp.sendRedirect(req.getContextPath() + "/TaskServlet");
        } else {
            logger.warning("Empty request body received in doPut");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            Long taskId = Long.valueOf(idStr);
            taskService.deleteTask(taskId);
            resp.sendRedirect(req.getContextPath() + "/TaskServlet");
        } else {
            logger.warning("No task ID provided for deletion");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No task ID provided");
        }
    }
}
