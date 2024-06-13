package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ProjectUserDAO;
import dto.ProjectUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.ProjectUserService;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static org.mockito.Mockito.*;

class ProjectUserServletTest {

    @Mock
    private ProjectUserService projectUserService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private ObjectMapper objectMapper;
    @Mock
    private RequestDispatcher requestDispatcher;

    private ProjectUserServlet projectUserServlet;
    private static final Logger logger = Logger.getLogger(ProjectUserServlet.class.getName());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        projectUserServlet = new ProjectUserServlet();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDoGet() throws Exception {
        List<ProjectUserDTO> projectUsers = new ArrayList<>();
        projectUsers.add(new ProjectUserDTO(1L, 1L));
        projectUsers.add(new ProjectUserDTO(2L, 2L));

        when(projectUserService.getAllProjectUsers()).thenReturn(projectUsers);
        when(request.getRequestDispatcher("projectUsers.jsp")).thenReturn(requestDispatcher);

        projectUserServlet.doGet(request, response);

        verify(request).setAttribute("projectUsers", projectUsers);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostWithValidData() throws Exception {
        String projectIdStr = "1";
        String userIdStr = "1";

        when(request.getParameter("projectId")).thenReturn(projectIdStr);
        when(request.getParameter("userId")).thenReturn(userIdStr);

        projectUserServlet.doPost(request, response);

        verify(projectUserService).assignUserToProject(any(ProjectUserDTO.class));
        verify(response).sendRedirect(request.getContextPath() + "/ProjectUserServlet");
    }

    @Test
    void testDoPostWithInvalidData() throws Exception {
        String projectIdStr = null;
        String userIdStr = null;

        when(request.getParameter("projectId")).thenReturn(projectIdStr);
        when(request.getParameter("userId")).thenReturn(userIdStr);

        projectUserServlet.doPost(request, response);

        verify(projectUserService, never()).assignUserToProject(any(ProjectUserDTO.class));
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
    }

    @Test
    void testDoDelete() throws Exception {
        String projectIdStr = "1";
        String userIdStr = "1";

        when(request.getParameter("projectId")).thenReturn(projectIdStr);
        when(request.getParameter("userId")).thenReturn(userIdStr);

        projectUserServlet.doDelete(request, response);

        verify(projectUserService).unassignUserFromProject(any(ProjectUserDTO.class));
        verify(response).sendRedirect(request.getContextPath() + "/ProjectUserServlet");
    }
}
