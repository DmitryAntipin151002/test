package services;

import static org.mockito.Mockito.*;
import dao.TaskDAO;
import dto.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Impl.TaskServiceImpl;
import service.TaskService;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceImplTest {
    private TaskService taskService;
    private TaskDAO taskDAO;

    @BeforeEach
    void setUp() {
        taskDAO = mock(TaskDAO.class);
        taskService = new TaskServiceImpl(taskDAO);
    }

    @Test
    void testCreateTask() {
        TaskDTO task = new TaskDTO(1L, "Task 1", "Description 1", 1L);

        taskService.createTask(task);

        verify(taskDAO, times(1)).createTask(task);
    }

    @Test
    void testGetTaskById() {
        Long taskId = 1L;
        TaskDTO task = new TaskDTO(taskId, "Task 1", "Description 1", 1L);

        when(taskDAO.getTaskById(taskId)).thenReturn(task);

        TaskDTO result = taskService.getTaskById(taskId);
        assertEquals(task, result);
        verify(taskDAO, times(1)).getTaskById(taskId);
    }

    @Test
    void testGetAllTasks() {
        List<TaskDTO> tasks = Arrays.asList(
                new TaskDTO(1L, "Task 1", "Description 1", 1L),
                new TaskDTO(2L, "Task 2", "Description 2", 2L)
        );

        when(taskDAO.getAllTasks()).thenReturn(tasks);

        List<TaskDTO> result = taskService.getAllTasks();
        assertEquals(2, result.size());
        verify(taskDAO, times(1)).getAllTasks();
    }

    @Test
    void testUpdateTask() {
        TaskDTO task = new TaskDTO(1L, "Task 1", "Description 1", 1L);

        taskService.updateTask(task);

        verify(taskDAO, times(1)).updateTask(task);
    }

    @Test
    void testDeleteTask() {
        Long taskId = 1L;

        taskService.deleteTask(taskId);

        verify(taskDAO, times(1)).deleteTask(taskId);
    }
}
