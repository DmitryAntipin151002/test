package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.lmpl.UserDAOImpl;
import dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServletTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    private UserServlet userServlet;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userServlet = new UserServlet(userService);
       objectMapper = new ObjectMapper();
    }



    @Test
    void testDoPostWithValidData() throws Exception {
        // Устанавливаем параметры запроса
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("passwordHash")).thenReturn("password123");
        when(request.getParameter("_method")).thenReturn(null); // Устанавливаем метод запроса

        // Вызываем метод, который тестируем
        userServlet.doPost(request, response);

        // Проверяем, что метод createUser вызван с любым объектом UserDTO
        verify(userService).createUser(any(UserDTO.class));
        // Проверяем, что произведено перенаправление на другую страницу
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testDoPostWithInvalidData() throws Exception {
        when(request.getParameter("email")).thenReturn(null);
        when(request.getParameter("passwordHash")).thenReturn("password123");

        userServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
    }



    @Test
    void testDoPutWithInvalidData() throws Exception {
        when(request.getParameter("id")).thenReturn(null);

        userServlet.doPut(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Request body is empty");
    }



    @Test
    void testDoDeleteWithInvalidData() throws Exception {
        when(request.getParameter("id")).thenReturn(null);

        userServlet.doDelete(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "No user ID provided");
    }
}
