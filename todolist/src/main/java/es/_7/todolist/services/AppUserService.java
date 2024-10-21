package es._7.todolist.services;


import es._7.todolist.repositories.AppUserRepository;
import org.springframework.stereotype.Service;
import es._7.todolist.models.AppUser;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository userRepository;

    public AppUserService (AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

}
