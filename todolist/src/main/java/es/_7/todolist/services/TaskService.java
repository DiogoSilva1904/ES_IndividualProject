package es._7.todolist.services;


import es._7.todolist.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import es._7.todolist.models.Task;

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


}
