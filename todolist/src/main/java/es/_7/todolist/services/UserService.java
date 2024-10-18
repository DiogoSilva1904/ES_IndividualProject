package es._7.todolist.services;


import es._7.todolist.repositories.UserRepository;
import org.springframework.stereotype.Service;
import es._7.todolist.models.User;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}