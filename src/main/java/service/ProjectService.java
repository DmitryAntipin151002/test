package service;

import dto.ProjectDTO;
import java.util.List;

public interface ProjectService {
    void createProject(ProjectDTO project);
    ProjectDTO getProjectById(Long id);
    List<ProjectDTO> getAllProjects();
    void updateProject(ProjectDTO project);
    void deleteProject(Long id);
}
