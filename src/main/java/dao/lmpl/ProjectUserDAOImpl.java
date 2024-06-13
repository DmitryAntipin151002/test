package dao.lmpl;

import dao.DatabaseManager;
import dao.ProjectUserDAO;
import dto.ProjectUserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectUserDAOImpl implements ProjectUserDAO {

    private static final String SQL_ASSIGN_USER_TO_PROJECT = "INSERT INTO project_users (project_id, user_id) VALUES (?, ?)";
    private static final String SQL_UNASSIGN_USER_FROM_PROJECT = "DELETE FROM project_users WHERE project_id = ? AND user_id = ?";
    private static final String SQL_GET_USERS_BY_PROJECT_ID = "SELECT user_id FROM project_users WHERE project_id = ?";
    private static final String SQL_GET_PROJECTS_BY_USER_ID = "SELECT project_id FROM project_users WHERE user_id = ?";
    private static final String SQL_GET_ALL_PROJECT_USERS = "SELECT project_id, user_id FROM project_users";
    private static final String SQL_GET_ALL_PROJECT_IDS = "SELECT id FROM project";
    private Connection getConnection() throws SQLException {
        return DatabaseManager.getConnection();
    }

    @Override
    public void assignUserToProject(ProjectUserDTO projectUser) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_ASSIGN_USER_TO_PROJECT)) {
            stmt.setLong(1, projectUser.getProjectId());
            stmt.setLong(2, projectUser.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unassignUserFromProject(ProjectUserDTO projectUser) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_UNASSIGN_USER_FROM_PROJECT)) {
            stmt.setLong(1, projectUser.getProjectId());
            stmt.setLong(2, projectUser.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Long> getUsersByProjectId(Long projectId) {
        List<Long> userIds = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_GET_USERS_BY_PROJECT_ID)) {
            stmt.setLong(1, projectId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userIds.add(rs.getLong("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userIds;
    }

    @Override
    public List<Long> getProjectsByUserId(Long userId) {
        List<Long> projectIds = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_GET_PROJECTS_BY_USER_ID)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                projectIds.add(rs.getLong("project_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectIds;
    }

    @Override
    public List<Long> getAllProjectIds() {
        List<Long> projectIds = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement( SQL_GET_ALL_PROJECT_IDS)){
             ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                projectIds.add(rs.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectIds;
    }

    @Override
    public List<ProjectUserDTO> getAllProjectUsers() {
        List<ProjectUserDTO> projectUsers = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL_GET_ALL_PROJECT_USERS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                projectUsers.add(new ProjectUserDTO(rs.getLong("project_id"), rs.getLong("user_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectUsers;
    }
}
