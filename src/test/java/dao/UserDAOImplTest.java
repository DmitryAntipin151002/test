package dao;
import dao.lmpl.UserDAOImpl;
import dto.UserDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
class UserDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("JavaDB")
            .withUsername("Ray_GEK")
            .withPassword("Dmitry151002123");

    private static UserDAO userDAO;

    @BeforeAll
    static void setUp() throws SQLException {
        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();

        // Инициализация DAO с подключением к тестовой БД
        userDAO = new UserDAOImpl(jdbcUrl, username, password);

        // Создание таблицы для тестов
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE users (id SERIAL PRIMARY KEY, email VARCHAR(255), password_hash VARCHAR(255))");
        }
    }

    @AfterAll
    static void tearDown() throws SQLException {
        // Удаление таблицы после тестов
        try (Connection conn = DriverManager.getConnection(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE users");
        }
    }

    @Test
    void createUser() {
        UserDTO user = new UserDTO(null, "test@example.com", "password123");
        userDAO.createUser(user);

        assertNotNull(user.getId());
    }

    @Test
    void getUserById() {
        UserDTO user = new UserDTO(null, "test@example.com", "password123");
        userDAO.createUser(user);

        UserDTO retrievedUser = userDAO.getUserById(user.getId());

        assertNotNull(retrievedUser);
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getPasswordHash(), retrievedUser.getPasswordHash());
    }

    @Test
    void getAllUsers() {
        userDAO.createUser(new UserDTO(null, "user1@example.com", "password123"));
        userDAO.createUser(new UserDTO(null, "user2@example.com", "password456"));

        List<UserDTO> users = userDAO.getAllUsers();

        assertEquals(2, users.size());
    }

    @Test
    void updateUser() {
        UserDTO user = new UserDTO(null, "test@example.com", "password123");
        userDAO.createUser(user);

        user.setEmail("updated@example.com");
        user.setPasswordHash("updatedPassword456");

        userDAO.updateUser(user);

        UserDTO updatedUser = userDAO.getUserById(user.getId());

        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(user.getPasswordHash(), updatedUser.getPasswordHash());
    }

    @Test
    void deleteUser() {
        UserDTO user = new UserDTO(null, "test@example.com", "password123");
        userDAO.createUser(user);

        userDAO.deleteUser(user.getId());

        UserDTO deletedUser = userDAO.getUserById(user.getId());

        assertNull(deletedUser);
    }
}
