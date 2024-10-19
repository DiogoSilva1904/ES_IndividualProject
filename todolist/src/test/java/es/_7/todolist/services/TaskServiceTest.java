package es._7.todolist.services;

import es._7.todolist.models.Task;
import es._7.todolist.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Slf4j
class TaskServiceTest {

        @Mock
        private TaskRepository taskRepository;

        @InjectMocks
        private TaskService taskService;

        @Test
        void saveTask() {
            Task task = new Task();
            task.setTitle("Task 1");
            task.setDescription("Description 1");

            when(taskRepository.save(any(Task.class))).thenReturn(task);

            Task savedTask = taskService.save(task);

            assertNotNull(savedTask);
            assertEquals(task.getTitle(), savedTask.getTitle());
            assertEquals(task.getDescription(), savedTask.getDescription());
        }

        @Test
        void findAll() {
            Task task1 = new Task();
            task1.setTitle("Task 1");
            task1.setDescription("Description 1");

            Task task2 = new Task();
            task2.setTitle("Task 2");
            task2.setDescription("Description 2");

            when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

            List<Task> tasks = taskService.findAll();

            assertNotNull(tasks);
            assertEquals(2, tasks.size());
            assertEquals(task1.getTitle(), tasks.get(0).getTitle());
            assertEquals(task1.getDescription(), tasks.get(0).getDescription());
            assertEquals(task2.getTitle(), tasks.get(1).getTitle());
            assertEquals(task2.getDescription(), tasks.get(1).getDescription());
        }
}
