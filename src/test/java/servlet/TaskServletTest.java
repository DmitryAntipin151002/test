package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.lmpl.TaskDAOImpl;
import dto.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.TaskService;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static org.mockito.Mockito.*;

class TaskServletTest {

    @Mock
    private TaskService taskService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    private TaskServlet taskServlet;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        taskServlet = new TaskServlet(taskService);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDoGet() throws Exception {
        List<TaskDTO> tasks = new ArrayList<>();
        tasks.add(new TaskDTO(1L, "Task 1", "Description 1", 1L));
        tasks.add(new TaskDTO(2L, "Task 2", "Description 2", 5L));

        when(taskService.getAllTasks()).thenReturn(tasks);
        when(request.getRequestDispatcher("tasks.jsp")).thenReturn(requestDispatcher);

        taskServlet.doGet(request, response);

        verify(request).setAttribute("tasks", tasks);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostWithValidData() throws Exception {
        String title = "New Task";
        String description = "New Task Description";
        String userIdStr = "1";

        when(request.getParameter("title")).thenReturn(title);
        when(request.getParameter("description")).thenReturn(description);
        when(request.getParameter("userId")).thenReturn(userIdStr);

        taskServlet.doPost(request, response);

        verify(taskService).createTask(any(TaskDTO.class));
        verify(response).sendRedirect(request.getContextPath() + "/TaskServlet");
    }

    @Test
    void testDoPostWithInvalidData() throws Exception {
        String title = null;
        String description = null;
        String userIdStr = null;

        when(request.getParameter("title")).thenReturn(title);
        when(request.getParameter("description")).thenReturn(description);
        when(request.getParameter("userId")).thenReturn(userIdStr);

        taskServlet.doPost(request, response);

        verify(taskService, never()).createTask(any(TaskDTO.class));
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
    }


}
