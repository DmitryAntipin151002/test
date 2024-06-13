package servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import dao.lmpl.ProjectDAOImpl;
import dto.ProjectDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.Impl.ProjectServiceImpl;
import service.ProjectService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static org.mockito.Mockito.*;

class ProjectServletTest {

    private ProjectServlet projectServlet;

    @Mock
    private ProjectService projectService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectServlet = new ProjectServlet();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDoGet() throws Exception {
        List<ProjectDTO> projects = new ArrayList<>();
        projects.add(new ProjectDTO(1L, "Project 1", "Description 1", null));
        projects.add(new ProjectDTO(2L, "Project 2", "Description 2", null));

        when(projectService.getAllProjects()).thenReturn(projects);
        when(request.getRequestDispatcher("projects.jsp")).thenReturn(requestDispatcher);

        projectServlet.doGet(request, response);

        verify(request).setAttribute("projects", projects);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPost() throws Exception {
        when(request.getParameter("name")).thenReturn("Test Project");
        when(request.getParameter("description")).thenReturn("Test Description");
        when(request.getContextPath()).thenReturn("/context");

        projectServlet.doPost(request, response);

        verify(projectService).createProject(any(ProjectDTO.class));
        verify(response).sendRedirect("/context/ProjectServlet");
    }

    @Test
    void testDoPostEmptyBody() throws Exception {
        when(request.getParameter("name")).thenReturn(null);
        when(request.getParameter("description")).thenReturn(null);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        projectServlet.doPost(request, response);

        verify(logger).warning("Empty request body received in doPost");
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
    }

    @Test
    void testDoPut() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Updated Project");
        when(request.getParameter("description")).thenReturn("Updated Description");
        when(request.getContextPath()).thenReturn("/context");

        projectServlet.doPut(request, response);

        verify(projectService).updateProject(any(ProjectDTO.class));
        verify(response).sendRedirect("/context/ProjectServlet");
    }

    @Test
    void testDoPutEmptyBody() throws Exception {
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("name")).thenReturn(null);
        when(request.getParameter("description")).thenReturn(null);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        projectServlet.doPut(request, response);

        verify(logger).warning("Empty request body received in doPut");
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
    }

    @Test
    void testDoDelete() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getContextPath()).thenReturn("/context");

        projectServlet.doDelete(request, response);

        verify(projectService).deleteProject(1L);
        verify(response).sendRedirect("/context/ProjectServlet");
    }

    @Test
    void testDoDeleteNoId() throws Exception {
        when(request.getParameter("id")).thenReturn(null);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        projectServlet.doDelete(request, response);

        verify(logger).warning("No project ID provided for deletion");
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "No project ID provided");
    }
}
