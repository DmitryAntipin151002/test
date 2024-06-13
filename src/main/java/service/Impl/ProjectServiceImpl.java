package service.Impl;

import dao.ProjectDAO;
import dto.ProjectDTO;
import service.ProjectService;
import java.util.List;

public class ProjectServiceImpl implements ProjectService {

    private final ProjectDAO projectDAO;

    public ProjectServiceImpl(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    public void createProject(ProjectDTO project) {
        projectDAO.createProject(project);
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        return projectDAO.getProjectById(id);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectDAO.getAllProjects();
    }

    @Override
    public void updateProject(ProjectDTO project) {
        projectDAO.updateProject(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectDAO.deleteProject(id);
    }
}
