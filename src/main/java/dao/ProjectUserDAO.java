package dao;

import dto.ProjectUserDTO;
import java.util.List;

public interface ProjectUserDAO {
    void assignUserToProject(ProjectUserDTO projectUser);
    void unassignUserFromProject(ProjectUserDTO projectUser);
    List<Long> getUsersByProjectId(Long projectId);
    List<Long> getProjectsByUserId(Long userId);

    List<Long> getAllProjectIds();

    List<ProjectUserDTO> getAllProjectUsers();
}
