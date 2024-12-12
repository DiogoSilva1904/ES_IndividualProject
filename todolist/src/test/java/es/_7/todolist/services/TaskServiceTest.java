package es._7.todolist.services;

import es._7.todolist.models.AppUser;
import es._7.todolist.models.Task;
import es._7.todolist.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestExecutionListeners;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Slf4j
class TaskServiceTest {

        @Mock
        private TaskRepository taskRepository;

        @InjectMocks
        private TaskService taskService;

        @Test
        void whenSaveTask_thenReturnTask() {
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
        void whenFindAllTasks_thenReturnAllTasks() {
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

       @Test
        void whenFindByUserAndPriorityTasks_thenReturnTasks() {
            AppUser user = new AppUser();
            user.setUsername("user1");
            
            Task task1 = new Task();
            task1.setTitle("Task 1");
            task1.setDescription("Description 1");
            task1.setPriority("HIGH");
            task1.setUser(user);

            Task task2 = new Task();
            task2.setTitle("Task 2");
            task2.setDescription("Description 2");
            task2.setPriority("HIGH");
            task2.setUser(user);

            // Update: Use eq("HIGH") instead of the raw string "HIGH"
            when(taskRepository.findByUserAndPriority(Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(task1, task2));

            // Call the service method
            List<Task> tasks = taskService.findByUserAndPriorityTasks(user, "HIGH");

            // Assertions to verify the results
            assertNotNull(tasks);
            assertEquals(2, tasks.size());
            assertEquals(task1.getTitle(), tasks.get(0).getTitle());
            assertEquals(task1.getDescription(), tasks.get(0).getDescription());
            assertEquals(task1.getUser(), tasks.get(0).getUser());
            assertEquals(task2.getTitle(), tasks.get(1).getTitle());
            assertEquals(task2.getDescription(), tasks.get(1).getDescription());
            assertEquals(task2.getUser(), tasks.get(1).getUser());
        }

        @Test
        void whenFindByPriorityTasks_thenReturnTasks() {
            Task task1 = new Task();
            task1.setTitle("Task 1");
            task1.setDescription("Description 1");
            task1.setPriority("HIGH");

            Task task2 = new Task();
            task2.setTitle("Task 2");
            task2.setDescription("Description 2");
            task2.setPriority("HIGH");

            when(taskRepository.findByPriority(Mockito.any())).thenReturn(Arrays.asList(task1, task2));

            List<Task> tasks = taskService.findByPriorityTasks("HIGH");

            assertNotNull(tasks);
            assertEquals(2, tasks.size());
            assertEquals(task1.getTitle(), tasks.get(0).getTitle());
            assertEquals(task1.getDescription(), tasks.get(0).getDescription());
            assertEquals(task2.getTitle(), tasks.get(1).getTitle());
            assertEquals(task2.getDescription(), tasks.get(1).getDescription());
        }

        @Test
        void whenDeleteTask_thenReturnVoid() {
            Task task = new Task();
            task.setId(1L);
            task.setTitle("Task 1");
            task.setDescription("Description 1");
            task.setPriority("HIGH");
            task.setCompletion_status("INCOMPLETE");
            task.setCategory("WORK");
            task.setCreation_date(LocalDate.now());
            task.setDeadline(LocalDate.now().plusDays(1));
            
            taskService.delete(1L);

            verify(taskRepository).deleteById(1L);
            verify(taskRepository, times(1)).deleteById(1L);
        }

        @Test
        void whenUpdateTask_ReturnUpdatedTask(){
            Task task = new Task();
            task.setId(1L);
            task.setTitle("Task 1");
            task.setDescription("Description 1");
            task.setPriority("HIGH");
            task.setCompletion_status("INCOMPLETE");
            task.setCategory("WORK");
            task.setCreation_date(LocalDate.now());
            task.setDeadline(LocalDate.now().plusDays(1));

            Task updatedTask = new Task();
            updatedTask.setId(1L);
            updatedTask.setTitle("Task 1");
            updatedTask.setDescription("Description updated");
            updatedTask.setPriority("MEDIUM");
            updatedTask.setCompletion_status("COMPLETE");
            updatedTask.setCategory("WORK_UPDATED");
            updatedTask.setCreation_date(LocalDate.now());
            updatedTask.setDeadline(LocalDate.now().plusDays(1));

            when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
            when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

            Task updated = taskService.updateTask(1L, updatedTask);

            assertNotNull(updated);
            assertEquals(updatedTask.getId(), updated.getId());
            assertEquals(updatedTask.getTitle(), updated.getTitle());
            assertEquals(updatedTask.getDescription(), updated.getDescription());
            assertEquals(updatedTask.getPriority(), updated.getPriority());
            assertEquals(updatedTask.getCompletion_status(), updated.getCompletion_status());
            assertEquals(updatedTask.getCategory(), updated.getCategory());
            assertEquals(updatedTask.getCreation_date(), updated.getCreation_date());
            assertEquals(updatedTask.getDeadline(), updated.getDeadline());
        }

}
