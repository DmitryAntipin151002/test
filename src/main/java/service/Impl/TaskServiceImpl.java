package service.Impl;

import dao.TaskDAO;
import dto.TaskDTO;
import service.TaskService;
import java.util.List;

public class TaskServiceImpl implements TaskService {

    private final TaskDAO taskDAO;

    public TaskServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public void createTask(TaskDTO task) {
        taskDAO.createTask(task);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return taskDAO.getTaskById(id);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    @Override
    public void updateTask(TaskDTO task) {
        taskDAO.updateTask(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskDAO.deleteTask(id);
    }
}
