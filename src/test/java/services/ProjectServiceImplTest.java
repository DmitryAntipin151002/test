package services;

import dao.ProjectDAO;
import dto.ProjectDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.Impl.ProjectServiceImpl;
import service.ProjectService;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest {

    private ProjectDAO projectDAO;
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectDAO = Mockito.mock(ProjectDAO.class);
        projectService = new ProjectServiceImpl(projectDAO);
    }

    @Test
    void createProject() {
        ProjectDTO project = new ProjectDTO();
        projectService.createProject(project);
        verify(projectDAO, times(1)).createProject(project);
    }

    @Test
    void getProjectById() {
        Long projectId = 1L;
        ProjectDTO project = new ProjectDTO();
        when(projectDAO.getProjectById(projectId)).thenReturn(project);

        ProjectDTO result = projectService.getProjectById(projectId);

        assertNotNull(result);
        assertEquals(project, result);
        verify(projectDAO, times(1)).getProjectById(projectId);
    }

    @Test
    void getAllProjects() {
        List<ProjectDTO> projects = Arrays.asList(new ProjectDTO(), new ProjectDTO());
        when(projectDAO.getAllProjects()).thenReturn(projects);

        List<ProjectDTO> result = projectService.getAllProjects();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectDAO, times(1)).getAllProjects();
    }

    @Test
    void updateProject() {
        ProjectDTO project = new ProjectDTO();
        projectService.updateProject(project);
        verify(projectDAO, times(1)).updateProject(project);
    }

    @Test
    void deleteProject() {
        Long projectId = 1L;
        projectService.deleteProject(projectId);
        verify(projectDAO, times(1)).deleteProject(projectId);
    }
}
