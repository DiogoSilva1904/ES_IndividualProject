package es._7.todolist.services;

import es._7.todolist.models.AppUser;
import es._7.todolist.repositories.AppUserRepository;
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
class AppUserServiceTest {

        @Mock
        private AppUserRepository userRepository;

        @InjectMocks
        private AppUserService userService;

        @Test
        void whenSaveUser_thenReturnUser() {
            AppUser user = new AppUser();
            user.setUsername("John Doe");
            user.setEmail("d@gmail.com");

            when(userRepository.save(any(AppUser.class))).thenReturn(user);

            AppUser savedUser = userService.save(user);

            assertNotNull(savedUser);
            assertEquals(user.getUsername(), savedUser.getUsername());
            assertEquals(user.getEmail(), savedUser.getEmail());
        }

        @Test
        void whenFindBySubId_thenReturnUser() {
            AppUser user = new AppUser();
            user.setUsername("John Doe");
            user.setEmail("d@gmail.com");
            user.setSubId("123");

            when(userRepository.findBySubId("123")).thenReturn(user);

            AppUser r_user = userService.findBySubId("123");

            assertNotNull(user);
            assertEquals(user.getUsername(), r_user.getUsername());
            assertEquals(user.getEmail(), r_user.getEmail());
            assertEquals(user.getSubId(), r_user.getSubId());

        }

}
