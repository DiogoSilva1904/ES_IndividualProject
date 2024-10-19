package es._7.todolist.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es._7.todolist.models.Task;
import es._7.todolist.repositories.TaskRepository;

@DataJpaTest
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void saveTask() {
        Task task = new Task();
        task.setTitle("Task 1");
        task.setDescription("Description 1");

        Task savedTask = taskRepository.save(task);

        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo(task.getTitle());
        assertThat(savedTask.getDescription()).isEqualTo(task.getDescription());
    }
}
