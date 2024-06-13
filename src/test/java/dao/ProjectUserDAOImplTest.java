package dao;

import dao.ProjectUserDAO;
import dao.lmpl.ProjectUserDAOImpl;
import dto.ProjectUserDTO;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectUserDAOImplTest {

    private static PostgreSQLContainer<?> postgreSQLContainer;
    private ProjectUserDAO projectUserDAO;

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

        projectUserDAO = new ProjectUserDAOImpl(jdbcUrl, username, password);
        initDatabase("postgresql://localhost:5432/JavaBD", "Ray_Gek", "Dmitry151002123");

    }

    @Test
    void testAssignUserToProject() {
        ProjectUserDTO projectUser = new ProjectUserDTO(1L, 2L);
        projectUserDAO.assignUserToProject(projectUser);
        List<Long> users = projectUserDAO.getUsersByProjectId(1L);
        assertTrue(users.contains(2L));
    }

    @Test
    void testUnassignUserFromProject() {
        ProjectUserDTO projectUser = new ProjectUserDTO(1L, 1L);
        projectUserDAO.unassignUserFromProject(projectUser);
        List<Long> users = projectUserDAO.getUsersByProjectId(1L);
        assertFalse(users.contains(1L));
    }

    @Test
    void testGetUsersByProjectId() {
        List<Long> users = projectUserDAO.getUsersByProjectId(1L);
        assertEquals(2, users.size());
        assertTrue(users.contains(1L));
        assertTrue(users.contains(2L));
    }

    @Test
    void testGetProjectsByUserId() {
        List<Long> projects = projectUserDAO.getProjectsByUserId(2L);
        assertEquals(1, projects.size());
        assertTrue(projects.contains(1L));
    }

    @Test
    void testGetAllProjectIds() {
        List<Long> projectIds = projectUserDAO.getAllProjectIds();
        assertEquals(2, projectIds.size());
        assertTrue(projectIds.contains(1L));
        assertTrue(projectIds.contains(2L));
    }

    private void initDatabase(String jdbcUrl, String username, String password) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             java.sql.Statement statement = conn.createStatement()) {
            statement.executeUpdate("CREATE TABLE project_users (" +
                    "project_id BIGINT, " +
                    "user_id BIGINT)");

            statement.executeUpdate("INSERT INTO project_users (project_id, user_id) VALUES " +
                    "(1, 1), " +
                    "(1, 2), " +
                    "(2, 2)");
        }
    }
}
