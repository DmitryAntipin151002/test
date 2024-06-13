package services;

import static org.mockito.Mockito.*;

import dao.ProjectUserDAO;
import dto.ProjectUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Impl.ProjectUserServiceImpl;
import service.ProjectUserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectUserServiceImplTest {
    private ProjectUserService projectUserService;
    private ProjectUserDAO projectUserDAO;

    @BeforeEach
    void setUp() {
        projectUserDAO = mock(ProjectUserDAO.class);
        projectUserService = new ProjectUserServiceImpl(projectUserDAO);
    }

    @Test
    void testAssignUserToProject() {
        ProjectUserDTO projectUser = new ProjectUserDTO(1L, 1L);

        projectUserService.assignUserToProject(projectUser);

        verify(projectUserDAO, times(1)).assignUserToProject(projectUser);
    }

    @Test
    void testUnassignUserFromProject() {
        ProjectUserDTO projectUser = new ProjectUserDTO(1L, 1L);

        projectUserService.unassignUserFromProject(projectUser);

        verify(projectUserDAO, times(1)).unassignUserFromProject(projectUser);
    }

    @Test
    void testGetUsersByProjectId() {
        Long projectId = 1L;
        List<Long> userIds = Arrays.asList(1L, 2L);

        when(projectUserDAO.getUsersByProjectId(projectId)).thenReturn(userIds);

        List<Long> result = projectUserService.getUsersByProjectId(projectId);
        assertEquals(userIds, result);
        verify(projectUserDAO, times(1)).getUsersByProjectId(projectId);
    }

    @Test
    void testGetProjectsByUserId() {
        Long userId = 1L;
        List<Long> projectIds = Arrays.asList(1L, 2L);

        when(projectUserDAO.getProjectsByUserId(userId)).thenReturn(projectIds);

        List<Long> result = projectUserService.getProjectsByUserId(userId);
        assertEquals(projectIds, result);
        verify(projectUserDAO, times(1)).getProjectsByUserId(userId);
    }

    @Test
    void testGetAllProjectUsers() {
        List<Long> projectIds = Arrays.asList(1L, 2L);
        List<Long> userIdsProject1 = Arrays.asList(1L, 2L);
        List<Long> userIdsProject2 = Arrays.asList(3L);

        when(projectUserDAO.getAllProjectIds()).thenReturn(projectIds);
        when(projectUserDAO.getUsersByProjectId(1L)).thenReturn(userIdsProject1);
        when(projectUserDAO.getUsersByProjectId(2L)).thenReturn(userIdsProject2);

        List<ProjectUserDTO> result = projectUserService.getAllProjectUsers();
        List<ProjectUserDTO> expected = Arrays.asList(
                new ProjectUserDTO(1L, 1L),
                new ProjectUserDTO(1L, 2L),
                new ProjectUserDTO(2L, 3L)
        );

        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
        verify(projectUserDAO, times(1)).getAllProjectIds();
        verify(projectUserDAO, times(1)).getUsersByProjectId(1L);
        verify(projectUserDAO, times(1)).getUsersByProjectId(2L);
    }
}
