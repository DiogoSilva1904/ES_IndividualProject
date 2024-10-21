package es._7.todolist.controllers;


import es._7.todolist.models.AppUser;
import es._7.todolist.services.AppUserService;
import jakarta.jws.soap.SOAPBinding;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
    private final AppUserService userService;

    public AppUserController(AppUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AppUser> save(@RequestBody AppUser user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
