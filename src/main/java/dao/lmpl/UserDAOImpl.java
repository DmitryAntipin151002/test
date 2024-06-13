package dao.lmpl;

import dao.DatabaseManager;
import dao.UserDAO;
import dto.UserDTO;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
public class UserDAOImpl implements UserDAO {

    private static final String SQL_INSERT_USER = "INSERT INTO users (email, password_hash) VALUES (?, ?)";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_UPDATE_USER = "UPDATE users SET email = ?, password_hash = ? WHERE id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";


    private Connection getConnection() throws SQLException {
        return DatabaseManager.getConnection();
    }

    @Override
    public void createUser(UserDTO user) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPasswordHash());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserDTO user = null;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_USERS);
            while (rs.next()) {
                UserDTO user = mapResultSetToUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void updateUser(UserDTO user) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_USER)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPasswordHash());
            stmt.setLong(3, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Long id) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_USER)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private UserDTO mapResultSetToUser(ResultSet rs) throws SQLException {
        return new UserDTO(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password_hash")
        );
    }
}
