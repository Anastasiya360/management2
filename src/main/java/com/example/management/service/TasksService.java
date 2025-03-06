package com.example.management.service;

import com.example.management.dto.TaskDto;
import com.example.management.entity.Tasks;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TasksService {

    /**
     * Удаление задачи по id
     */
    void deleteById(Integer taskId);

    /**
     * Создание задачи
     *
     * @return созданная задача
     */
    Tasks create(Tasks tasks);

    /**
     * Получение всех задач
     *
     * @return все задачи
     */
    List<Tasks> getAll(Pageable pageable);

    /**
     * Получение задачи по id
     *
     * @return найденная задача
     */
    Tasks findById(Integer taskId);

    /**
     * Изменение статуса задачи, только для исполнителей задачи
     *
     * @return задача с измененным статусом
     */
    Tasks changeStatus(Integer taskId, String status);

    /**
     * Назначение исполнителя задачи, только для автора задачи
     *
     * @return задача с назначенным исполнителем
     */
    Tasks appointExecutor(Integer taskId, Integer executorId);

    /**
     * Получение всех задач по исполнителю задачи
     *
     * @return список задач
     */
    List<TaskDto> findTaskByExecutorId(Integer userId, Pageable pageable, String title, String description, String status, String priority);

    /**
     * Получение всех задач по автору задачи
     *
     * @return список задач
     */
    List<TaskDto> findTaskByAuthorId(Integer userId, Pageable pageable, String title, String description, String status,String priority);

    /**
     * Изменение заголовка и описания задачи, только для автора задачи
     *
     * @return измененная задача
     */
    Tasks changeTask(Integer taskId, String title, String description);
}
