package es._7.todolist.services;

import es._7.todolist.models.User;
import es._7.todolist.repositories.UserRepository;
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
class UserServiceTest {

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private UserService userService;

        @Test
        void saveUser() {
            User user = new User();
            user.setUsername("John Doe");
            user.setEmail("d@gmail.com");

            when(userRepository.save(any(User.class))).thenReturn(user);

            User savedUser = userService.save(user);

            assertNotNull(savedUser);
            assertEquals(user.getUsername(), savedUser.getUsername());
            assertEquals(user.getEmail(), savedUser.getEmail());
        }

}
