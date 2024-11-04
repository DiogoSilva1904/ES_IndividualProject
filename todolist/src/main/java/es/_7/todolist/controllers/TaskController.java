package es._7.todolist.controllers;


import es._7.todolist.models.Task;
import es._7.todolist.models.AppUser;
import es._7.todolist.services.AppUserService;
import es._7.todolist.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final AppUserService userService;

    public TaskController(TaskService taskService, AppUserService appUserService) {
        this.taskService = taskService;
        this.userService = appUserService;
    }

    @PostMapping
    public ResponseEntity<Task> save(@RequestBody Task task, @AuthenticationPrincipal UserDetails userdetails) {
        String subID = userdetails.getUsername();
        if (subID == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Check if user exists by subID, or create if not found
        AppUser user = userService.findBySubId(subID);
        System.out.println("User: " + user);
        if (user == null) {
            user = new AppUser();
            user.setSubId(subID);
            userService.save(user);
        }
        task.setUser(user);
        System.out.println("Task: " + task);
        Task savedTask = taskService.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> findByPriorityTasks(@AuthenticationPrincipal UserDetails userdetails, @PathVariable String priority) {
        String subID = userdetails.getUsername();
        AppUser user = userService.findBySubId(subID);
        return ResponseEntity.ok(taskService.findByUserAndPriorityTasks(user, priority));
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userdetails) {
        String subID = userdetails.getUsername();
        Task task = taskService.findById(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 if the task doesn't exist
        }
        if (!task.getUser().getSubId().equals(subID)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // Return 403 if user is not the owner
        }
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task,@AuthenticationPrincipal UserDetails userdetails) {
        String subID = userdetails.getUsername();
        Task task1 = taskService.findById(id);
        if (task1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
        if (!task1.getUser().getSubId().equals(subID)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); 
        }
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }

}
