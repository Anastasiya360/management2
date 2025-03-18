package com.example.management;

import com.example.management.entity.Task;
import com.example.management.entity.User;
import com.example.management.exceptoin.ApiException;
import com.example.management.repository.TasksRepository;
import com.example.management.repository.UserRepository;
import com.example.management.service.TasksService;
import com.example.management.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


@SpringBootTest
class ManagementApplicationTests {
    @Autowired
    private TasksService tasksService;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TasksRepository tasksRepository;


    @Test
    void testTaskDeleteNotFound() {
        Mockito.when(tasksRepository.findById(1)).thenReturn(Optional.empty());

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.deleteById(1);
        });
        Assertions.assertEquals(404, thrown.getStatusCode());
        Assertions.assertEquals("Задача не найдена", thrown.getMessage());
    }

    @Test
    void testTaskDeleteSuccess() {
        Task task = new Task();
        task.setId(6);
        User author = new User();
        author.setId(1);
        task.setAuthor(author);
        Mockito.when(tasksRepository.findById(6)).thenReturn(Optional.of(task));

        mockSecurity(1);

        Assertions.assertDoesNotThrow(() -> {
            tasksService.deleteById(6);
        });
    }

    @Test
    void testTaskCreateTitleNull() {
        Task task = new Task(2, null, "Description", "PENDING", "LOW");
        mockSecurity(12);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.create(task);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Заголовок не передан", thrown.getMessage());
    }

    @Test
    void testTaskCreateTitleIsBlank() {
        Task task = new Task(2, " ", "Description", "PENDING", "LOW");
        mockSecurity(12);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.create(task);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Заголовок не передан", thrown.getMessage());
    }

    @Test
    void testTaskCreateDescriptionNull() {
        Task task = new Task(2, "Title", null, "PENDING", "LOW");
        mockSecurity(12);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.create(task);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Описание не передано", thrown.getMessage());
    }

    @Test
    void testTaskCreateDescriptionIsBlank() {
        Task task = new Task(2, "Title", "", "PENDING", "LOW");
        mockSecurity(12);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.create(task);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Описание не передано", thrown.getMessage());
    }

    @Test
    void testTaskCreatePriorityIsBlank() {
        Task task = new Task(2, "Title", "Description", "PENDING", "");
        mockSecurity(12);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.create(task);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Приоритет не передан", thrown.getMessage());
    }

    @Test
    void testTaskCreatePriorityNull() {
        Task task = new Task(2, "Title", "Description", "PENDING", null);
        mockSecurity(12);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.create(task);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Приоритет не передан", thrown.getMessage());
    }

    @Test
    void testTaskCreatePriorityNotValid() {
        Task task = new Task(2, "Title", "Description", "PENDING", "BIG");
        mockSecurity(12);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.create(task);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Приоритет задачи задан не верно", thrown.getMessage());
    }

    @Test
    void testTaskCreatePrioritySuccess() {
        Task task = new Task(2, "Title", "Description", "PENDING", "LOW");
        mockSecurity(12);

        Assertions.assertDoesNotThrow(() -> {
            tasksService.create(task);
        });
    }

    @Test
    void testTaskChangeStatusNotFound() {
        Mockito.when(tasksRepository.findById(1)).thenReturn(Optional.empty());

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.changeStatus(1, "IN_PROGRESS");
        });
        Assertions.assertEquals(404, thrown.getStatusCode());
        Assertions.assertEquals("Задача не найдена", thrown.getMessage());
    }

    @Test
    void testTaskChangeStatusNotValid() {
        User user = new User();
        user.setId(12);
        Task task = new Task(2, "Title", "Description", "PENDING", "BIG", user, user);
        Mockito.when(tasksRepository.findById(2)).thenReturn(Optional.of(task));
        mockSecurity(12);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.changeStatus(2, "LOW");
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Статус задачи задан не верно", thrown.getMessage());
    }

    @Test
    void testTaskChangeStatusSuccess() {
        User user = new User();
        user.setId(12);
        Task task = new Task(2, "Title", "Description", "PENDING", "BIG", user, user);
        Mockito.when(tasksRepository.findById(2)).thenReturn(Optional.of(task));
        mockSecurity(12);

        Assertions.assertDoesNotThrow(() -> {
            tasksService.changeStatus(2, "IN_PROGRESS");
        });
    }

    @Test
    void testTaskAppointmentExecutorNotFound() {
        Mockito.when(tasksRepository.findById(1)).thenReturn(Optional.empty());
        mockSecurity(12);
        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            tasksService.appointExecutor(1, 12);
        });
        Assertions.assertEquals(404, thrown.getStatusCode());
        Assertions.assertEquals("Задача не найдена", thrown.getMessage());
    }

    @Test
    void testTaskAppointmentExecutorSuccess() {
        User author = new User();
        author.setId(12);
        User executor = new User();
        executor.setId(5);
        Task task = new Task(2, "Title", "Description", "PENDING", "LOW", author);
        Mockito.when(tasksRepository.findById(2)).thenReturn(Optional.of(task));
        Mockito.when(userRepository.findById(5)).thenReturn(Optional.of(executor));
        mockSecurity(12);

        Assertions.assertDoesNotThrow(() -> {
            tasksService.appointExecutor(2, 5);
        });
    }

    @Test
    void testUserCreateNameNull() {
        User user = new User(null, "Surname", "ADMIN","123@gmail.com", "12345678");

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Имя не передано", thrown.getMessage());
    }

    @Test
    void testUserCreateNameIsBlank() {
        User user = new User("", "Surname","ADMIN", "123@gmail.com", "12345678");

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Имя не передано", thrown.getMessage());
    }

    @Test
    void testUserCreateEmailNull() {
        User user = new User("Name", "Surname","ADMIN", null, "12345678");

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Email не передан или имеет менее 5 символов", thrown.getMessage());
    }

    @Test
    void testUserCreateEmailIsBlank() {
        User user = new User("Name", "Surname","ADMIN", "", "12345678");

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Email не передан или имеет менее 5 символов", thrown.getMessage());
    }

    @Test
    void testUserCreateEmailNotValid() {
        User user = new User("Name", "Surname","ADMIN", "1234", "12345678");

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Email не передан или имеет менее 5 символов", thrown.getMessage());
    }

    @Test
    void testUserCreatePasswordIsBlank() {
        User user = new User("Name", "Surname","ADMIN", "123@gmail.com", null);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Пароль не передан или имеет менее 8 символов", thrown.getMessage());
    }

    @Test
    void testUserCreatePasswordNull() {
        User user = new User("Name", "Surname","ADMIN", "123@gmail.com", "");

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Пароль не передан или имеет менее 8 символов", thrown.getMessage());
    }

    @Test
    void testUserCreatePasswordNotValid() {
        User user = new User("Name", "Surname","ADMIN", "123@gmail.com", "12345");

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            userService.create(user);
        });
        Assertions.assertEquals(400, thrown.getStatusCode());
        Assertions.assertEquals("Пароль не передан или имеет менее 8 символов", thrown.getMessage());
    }

    @Test
    void testUserGetByEmailNotFound() {
        Mockito.when(userRepository.findUserByEmail("123@gmail.com")).thenReturn(null);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            userService.getByUserEmail("123@gmail.com");
        });
        Assertions.assertEquals(404, thrown.getStatusCode());
        Assertions.assertEquals("Пользователь не найден", thrown.getMessage());
    }

    @Test
    void testUserGetByEmailSuccess() {
        User user = new User("Name", "Surname","ADMIN", "123@gmail.com", "12345678");
        Mockito.when(userRepository.findUserByEmail("123@gmail.com")).thenReturn(user);

        Assertions.assertDoesNotThrow(() -> {
            userRepository.findUserByEmail("123@gmail.com");
        });
    }

    private void mockSecurity(Integer currentUserIdMock) {
        User currentUserMock = new User();
        currentUserMock.setId(currentUserIdMock);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(currentUserMock);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

}
