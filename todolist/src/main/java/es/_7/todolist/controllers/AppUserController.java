package es._7.todolist.controllers;


import es._7.todolist.models.AppUser;
import es._7.todolist.services.AppUserService;
import jakarta.jws.soap.SOAPBinding;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "https://es-ua.ddns.net")
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

}
