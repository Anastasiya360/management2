package com.example.management.controller;

import com.example.management.dto.TaskDto;
import com.example.management.entity.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@Tag(name = "Tasks", description = "Interaction with tasks")
@ApiResponse(responseCode = "" + HttpServletResponse.SC_INTERNAL_SERVER_ERROR, description = "Внутренняя ошибка сервера")
public interface TasksController {

    @PreAuthorize("hasAnyAuthority('admin')")
    @Operation(
            summary = "Delete task",
            description = "Delete task by id"
    )
    @DeleteMapping(path = "/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "Запрос выполнен успешно"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_NOT_FOUND, description = "Задача не найдена"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_FORBIDDEN, description = "Пользователь не автор задачи")
    })
    void deleteById(@PathVariable Integer id);

    @PreAuthorize("hasAnyAuthority('admin')")
    @Operation(
            summary = "Create task"
    )
    @PostMapping(path = "/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "Запрос выполнен успешно",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST,
                    description = """
                            * Заголовок не передан
                            * Описание не передано
                            * Приоритет не передан
                            * Приоритет задачи задан не верно
                            """)
    })
    Task create(@RequestBody Task tasks);

    @PreAuthorize("hasAnyAuthority('admin')")
    @Operation(
            summary = "Get information about all tasks"
    )
    @GetMapping(path = "/get/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "Запрос выполнен успешно",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Task.class)))),
    })
    List<Task> getAll(@RequestParam(required = false) Integer pageNum,
                      @RequestParam(required = false) Integer pageSize);

    @Operation(
            summary = "Change task's status",
            description = "Change task's status by id, only for executor"
    )
    @PutMapping(path = "/change/status/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "Запрос выполнен успешно"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_NOT_FOUND, description = "Задача не найдена"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_FORBIDDEN, description = "Пользователь не автор задачи"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_BAD_REQUEST, description = "Статус задачи задан не верно")
    })
    void changeStatus(@PathVariable Integer id, @RequestParam String status);

    @PreAuthorize("hasAnyAuthority('admin')")
    @Operation(
            summary = "Change task's executor",
            description = "Change task's executor by id, only for author"
    )
    @PutMapping(path = "/appoint/executor/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "Запрос выполнен успешно"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_NOT_FOUND, description = "Задача не найдена"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_FORBIDDEN, description = "Пользователь не автор задачи")
    })
    void appointExecutor(@PathVariable Integer id, @RequestParam Integer executorId);

    @Operation(
            summary = "Get information about all tasks by executor"
    )

    @GetMapping(path = "/get/all/by/executor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "Запрос выполнен успешно",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Task.class)))),
    })
    List<TaskDto> findTaskByExecutorId(@RequestParam Integer userId,
                                       @RequestParam(required = false) Integer pageNum,
                                       @RequestParam(required = false) Integer pageSize,
                                       @RequestParam(required = false) String title,
                                       @RequestParam(required = false) String description,
                                       @RequestParam(required = false) String status,
                                       @RequestParam(required = false) String priority);

    @PreAuthorize("hasAnyAuthority('admin')")
    @Operation(
            summary = "Get information about all tasks by author"
    )

    @GetMapping(path = "/get/all/by/author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "Запрос выполнен успешно",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Task.class)))),
    })
    List<TaskDto> findTaskByAuthorId(@RequestParam Integer userId,
                                     @RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(required = false) String description,
                                     @RequestParam(required = false) String status,
                                     @RequestParam(required = false) String priority);

    @PreAuthorize("hasAnyAuthority('admin')")
    @Operation(
            summary = "Change task's title, description",
            description = "Change task's title, description by id, only for author"
    )
    @PutMapping(path = "task/change/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_OK, description = "Запрос выполнен успешно"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_NOT_FOUND, description = "Задача не найдена"),
            @ApiResponse(responseCode = "" + HttpServletResponse.SC_FORBIDDEN, description = "Пользователь не автор задачи")
    })
    void changeTask(@PathVariable Integer id, @RequestParam String title, @RequestParam String description);
}
