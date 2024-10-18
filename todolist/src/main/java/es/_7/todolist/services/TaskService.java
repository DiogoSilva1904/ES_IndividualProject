package es._7.todolist.services;


import es._7.todolist.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import es._7.todolist.models.Task;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }


}
