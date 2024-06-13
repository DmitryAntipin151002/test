package dao;

import dao.lmpl.TaskDAOImpl;
import dto.TaskDTO;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskDAOImplTest {

    private static PostgreSQLContainer<?> postgreSQLContainer;
    private TaskDAO taskDAO;

    @BeforeAll
    static void startContainer() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");
        postgreSQLContainer.start();
    }

    @AfterAll
    static void stopContainer() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        taskDAO = new TaskDAOImpl(jdbcUrl, username, password);
        initDatabase("postgresql://localhost:5432/JavaBD", "Ray_Gek", "Dmitry151002123");

    }

    @Test
    void testCreateTask() {
        TaskDTO task = new TaskDTO("New Task", "New Task Description", 1L);
        taskDAO.createTask(task);
        assertNotNull(task.getId());
    }

    @Test
    void testGetTaskById() {
        TaskDTO task = taskDAO.getTaskById(1L);
        assertNotNull(task);
        assertEquals(1L, task.getId());
        assertEquals("Task 1", task.getTitle());
        assertEquals("Description 1", task.getDescription());
        assertEquals(1L, task.getUserId());
    }

    @Test
    void testGetAllTasks() {
        List<TaskDTO> tasks = taskDAO.getAllTasks();
        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
    }

    @Test
    void testUpdateTask() {
        TaskDTO task = new TaskDTO(1L, "Updated Task", "Updated Task Description", 2L);
        taskDAO.updateTask(task);
        TaskDTO updatedTask = taskDAO.getTaskById(1L);
        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getTitle());
        assertEquals("Updated Task Description", updatedTask.getDescription());
        assertEquals(2L, updatedTask.getUserId());
    }

    @Test
    void testDeleteTask() {
        taskDAO.deleteTask(1L);
        assertNull(taskDAO.getTaskById(1L));
    }

    private void initDatabase(String jdbcUrl, String username, String password) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             java.sql.Statement statement = conn.createStatement()) {
            statement.executeUpdate("CREATE TABLE tasks (" +
                    "id SERIAL PRIMARY KEY, " +
                    "title VARCHAR(255) NOT NULL, " +
                    "description TEXT, " +
                    "user_id BIGINT)");

            statement.executeUpdate("INSERT INTO tasks (title, description, user_id) VALUES " +
                    "('Task 1', 'Description 1', 1), " +
                    "('Task 2', 'Description 2', 2)");
        }
    }
}

