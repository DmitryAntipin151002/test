package dao.lmpl;

import dao.DatabaseManager;
import dao.TaskDAO;
import dto.TaskDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {

    private static final String SQL_INSERT_TASK = "INSERT INTO tasks (title, description, user_id) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_TASK_BY_ID = "SELECT * FROM tasks WHERE id = ?";
    private static final String SQL_SELECT_ALL_TASKS = "SELECT * FROM tasks";
    private static final String SQL_UPDATE_TASK = "UPDATE tasks SET title = ?, description = ?, user_id = ? WHERE id = ?";
    private static final String SQL_DELETE_TASK = "DELETE FROM tasks WHERE id = ?";

    private Connection getConnection() throws SQLException {
        return DatabaseManager.getConnection();
    }

    @Override
    public void createTask(TaskDTO task) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_TASK, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setLong(3, task.getUserId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                task.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        TaskDTO task = null;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_TASK_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                task = mapResultSetToTask(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<TaskDTO> tasks = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_TASKS);
            while (rs.next()) {
                TaskDTO task = mapResultSetToTask(rs);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public void updateTask(TaskDTO task) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_TASK)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setLong(3, task.getUserId());
            stmt.setLong(4, task.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(Long id) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_TASK)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private TaskDTO mapResultSetToTask(ResultSet rs) throws SQLException {
        return new TaskDTO(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getLong("user_id")
        );
    }
}

