package service;

import dto.TaskDTO;
import java.util.List;

public interface TaskService {
    void createTask(TaskDTO task);
    TaskDTO getTaskById(Long id);
    List<TaskDTO> getAllTasks();
    void updateTask(TaskDTO task);
    void deleteTask(Long id);
}
