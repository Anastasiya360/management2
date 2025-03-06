package com.example.management.controller.impl;

import com.example.management.controller.TasksController;
import com.example.management.dto.TaskDto;
import com.example.management.entity.Tasks;
import com.example.management.service.TasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TasksControllerImpl implements TasksController {

    private final TasksService tasksService;

    @Override
    public void deleteById(Integer id) {
        tasksService.deleteById(id);
    }

    @Override
    public Tasks create(Tasks tasks) {
        return tasksService.create(tasks);
    }

    @Override
    public List<Tasks> getAll(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            return tasksService.getAll(null);
        }
        return tasksService.getAll(PageRequest.of(pageNum, pageSize));
    }

    @Override
    public void changeStatus(Integer id, String status) {
        tasksService.changeStatus(id, status);
    }

    @Override
    public void appointExecutor(Integer id, Integer executorId) {
        tasksService.appointExecutor(id, executorId);
    }

    @Override
    public List<TaskDto> findTaskByExecutorId(Integer userId, Integer pageSize, Integer pageNum, String title, String description, String status, String priority) {
        if (pageNum == null || pageSize == null) {
            return tasksService.findTaskByExecutorId(userId, null, title, description, status, priority);
        }
        return tasksService.findTaskByExecutorId(userId, PageRequest.of(pageNum, pageSize), title, description, status, priority);

    }

    @Override
    public List<TaskDto> findTaskByAuthorId(Integer userId, Integer pageSize, Integer pageNum, String title, String description, String status, String priority) {
        if (pageNum == null || pageSize == null) {
            return tasksService.findTaskByAuthorId(userId, null, title, description, status, priority);
        }
        return tasksService.findTaskByAuthorId(userId, PageRequest.of(pageNum, pageSize),title, description, status, priority);
    }

    @Override
    public void changeTask(Integer id, String title, String description) {
        tasksService.changeTask(id, title, description);
    }
}
