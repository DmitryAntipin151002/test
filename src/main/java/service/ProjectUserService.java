package service;

import dto.ProjectUserDTO;
import java.util.List;

public interface ProjectUserService {
    void assignUserToProject(ProjectUserDTO projectUser);
    void unassignUserFromProject(ProjectUserDTO projectUser);
    List<Long> getUsersByProjectId(Long projectId);
    List<Long> getProjectsByUserId(Long userId);
    List<ProjectUserDTO> getAllProjectUsers();

}
