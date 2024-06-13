package service.Impl;

import dao.ProjectUserDAO;
import dto.ProjectUserDTO;
import service.ProjectUserService;
import java.util.ArrayList;
import java.util.List;

public class ProjectUserServiceImpl implements ProjectUserService {

    private final ProjectUserDAO projectUserDAO;

    public ProjectUserServiceImpl(ProjectUserDAO projectUserDAO) {
        this.projectUserDAO = projectUserDAO;
    }

    @Override
    public void assignUserToProject(ProjectUserDTO projectUser) {
        projectUserDAO.assignUserToProject(projectUser);
    }

    @Override
    public void unassignUserFromProject(ProjectUserDTO projectUser) {
        projectUserDAO.unassignUserFromProject(projectUser);
    }

    @Override
    public List<Long> getUsersByProjectId(Long projectId) {
        return projectUserDAO.getUsersByProjectId(projectId);
    }

    @Override
    public List<Long> getProjectsByUserId(Long userId) {
        return projectUserDAO.getProjectsByUserId(userId);
    }

    @Override
    public List<ProjectUserDTO> getAllProjectUsers() {
        List<ProjectUserDTO> projectUsers = new ArrayList<>();
        List<Long> projectIds = projectUserDAO.getAllProjectIds();
        for (Long projectId : projectIds) {
            List<Long> userIds = projectUserDAO.getUsersByProjectId(projectId);
            for (Long userId : userIds) {
                projectUsers.add(new ProjectUserDTO(projectId, userId));
            }
        }
        return projectUsers;
    }

}
