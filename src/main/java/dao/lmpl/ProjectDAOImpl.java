package dao.lmpl;

import dao.DatabaseManager;
import dao.ProjectDAO;
import dto.ProjectDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImpl implements ProjectDAO {

    private static final String SQL_INSERT_PROJECT = "INSERT INTO project (name, description) VALUES (?, ?)";
    private static final String SQL_SELECT_PROJECT_BY_ID = "SELECT * FROM project WHERE id = ?";
    private static final String SQL_SELECT_ALL_PROJECTS = "SELECT * FROM project";
    private static final String SQL_UPDATE_PROJECT = "UPDATE project SET name = ?, description = ? WHERE id = ?";
    private static final String SQL_DELETE_PROJECT = "DELETE FROM project WHERE id = ?";

    private Connection getConnection() throws SQLException {
        return DatabaseManager.getConnection();
    }

    @Override
    public void createProject(ProjectDTO project) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_PROJECT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                project.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        ProjectDTO project = null;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_PROJECT_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                project = mapResultSetToProject(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<ProjectDTO> projects = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_PROJECTS);
            while (rs.next()) {
                ProjectDTO project = mapResultSetToProject(rs);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public void updateProject(ProjectDTO project) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_PROJECT)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setLong(3, project.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProject(Long id) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_PROJECT)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ProjectDTO mapResultSetToProject(ResultSet rs) throws SQLException {
        return new ProjectDTO(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                null // Не получаем задачи в этом методе, их можно загрузить отдельным запросом или использовать lazy loading
        );
    }
}
