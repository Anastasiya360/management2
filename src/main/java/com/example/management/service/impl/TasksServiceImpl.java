package com.example.management.service.impl;

import com.example.management.dto.TaskDto;
import com.example.management.entity.Tasks;
import com.example.management.entity.User;
import com.example.management.enums.Priority;
import com.example.management.enums.TaskStatus;
import com.example.management.exceptoin.ApiException;
import com.example.management.repository.TasksRepository;
import com.example.management.repository.UserRepository;
import com.example.management.service.TasksService;
import com.example.management.service.UserService;
import com.example.management.util.TuplesToDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TasksServiceImpl implements TasksService {

    private final UserService userService;
    private final TasksRepository tasksRepository;
    private final UserRepository userRepository;

    @Override
    public void deleteById(Integer taskId) {
        findById(taskId);
        tasksRepository.deleteById(taskId);
    }

    @Override
    public Tasks create(Tasks tasks) {
        tasks.setId(null);
        tasks.setStatus("pending");
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tasks.setAuthor(currentUser);
        checkParam(tasks);
        if (!EnumUtils.isValidEnum(Priority.class, tasks.getPriority())) {
            throw new ApiException("Приоритет задачи задан не верно", HttpServletResponse.SC_BAD_REQUEST);
        }
        return tasksRepository.save(tasks);
    }

    @Override
    public List<Tasks> getAll(Pageable pageable) {
        if (pageable == null) {
            return tasksRepository.findAll();
        } else {
            return tasksRepository.findAll(pageable).getContent();
        }
    }

    @Override
    public Tasks findById(Integer taskId) {
        if (taskId == null) {
            throw new ApiException("Идентификатор задачи не задан", HttpServletResponse.SC_BAD_REQUEST);
        }
        Optional<Tasks> task = tasksRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new ApiException("Задача не найдена", HttpServletResponse.SC_NOT_FOUND);
        }
        return task.get();
    }

    @Override
    public Tasks changeStatus(Integer taskId, String status) {
        Tasks task = findById(taskId);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ((!currentUser.getId().equals(task.getExecutor().getId())) && (!currentUser.getRole().equals("admin"))) {
            throw new ApiException("Пользователь не может изменить статус", HttpServletResponse.SC_FORBIDDEN);
        }
        if (!EnumUtils.isValidEnum(TaskStatus.class, status)) {
            throw new ApiException("Статус задачи задан не верно", HttpServletResponse.SC_BAD_REQUEST);
        }
        task.setStatus(status);
        return tasksRepository.save(task);
    }

    @Override
    public Tasks appointExecutor(Integer taskId, Integer executorId) {
        Tasks task = findById(taskId);
        task.setExecutor(userService.findById(executorId));
        return tasksRepository.save(task);
    }

    @Override
    public List<TaskDto> findTaskByExecutorId(Integer userId, Pageable pageable, String title, String description, String status, String priority) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ((!currentUser.getId().equals(userRepository.findById(userId).get().getId())) && (!currentUser.getRole().equals("admin"))) {
            throw new ApiException("Недостаточно прав для получения списка задач", HttpServletResponse.SC_FORBIDDEN);
        }
        if (pageable == null) {
            return TuplesToDto.tuplesToDto(tasksRepository.findTaskByExecutorId(userId, title, description, status, priority));
        } else {
            return TuplesToDto.tuplesToDto(tasksRepository.findTaskByExecutorId(userId, pageable, title, description, status, priority).getContent());
        }
    }

    @Override
    public List<TaskDto> findTaskByAuthorId(Integer userId, Pageable pageable, String title, String description, String status, String priority) {
        if (pageable == null) {
            return TuplesToDto.tuplesToDto(tasksRepository.findTaskByAuthorId(userId, title, description, status, priority));
        } else {
            return TuplesToDto.tuplesToDto(tasksRepository.findTaskByAuthorId(userId, pageable, title, description, status, priority).getContent());
        }
    }

    @Override
    public Tasks changeTask(Integer taskId, String title, String description) {
        Tasks task = findById(taskId);
        task.setTitle(title);
        task.setDescription(description);
        return tasksRepository.save(task);
    }

    private void checkParam(Tasks tasks) {
        if (tasks.getTitle() == null || tasks.getTitle().isBlank()) {
            throw new ApiException("Заголовок не передан", HttpServletResponse.SC_BAD_REQUEST);
        }
        if (tasks.getDescription() == null || tasks.getDescription().isBlank()) {
            throw new ApiException("Описание не передано", HttpServletResponse.SC_BAD_REQUEST);
        }
        if (tasks.getPriority() == null || tasks.getPriority().isBlank()) {
            throw new ApiException("Приоритет не передан", HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

