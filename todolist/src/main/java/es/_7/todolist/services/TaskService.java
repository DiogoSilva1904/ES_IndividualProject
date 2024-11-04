package es._7.todolist.services;


import es._7.todolist.repositories.TaskRepository;

import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.stereotype.Service;
import es._7.todolist.models.Task;
import es._7.todolist.models.AppUser;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).get();
    }

    public List<Task> findByUserAndPriorityTasks(AppUser user, String priority) {
        return taskRepository.findByUserAndPriority(user, priority);
    }

    public List<Task> findByPriorityTasks(String priority) {
        return taskRepository.findByPriority(priority);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, Task task) {
        Task taskToUpdate = taskRepository.findById(id).get();
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setDeadline(task.getDeadline());
        taskToUpdate.setPriority(task.getPriority());
        taskToUpdate.setCategory(task.getCategory());
        taskToUpdate.setCompletion_status(task.getCompletion_status());
        return taskRepository.save(taskToUpdate);
    }

}
