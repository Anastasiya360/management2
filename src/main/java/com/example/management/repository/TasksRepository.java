package com.example.management.repository;

import com.example.management.entity.Tasks;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {
    @Query(nativeQuery = true, value = "select tasks.id, tasks.title, tasks.description, tasks.status, tasks.priority, tasks.user_id_author, tasks.user_id_executor from management.tasks" +
            "         where (tasks.user_id_executor = :userId)" +
            "and (tasks.title is null or tasks.title like concat('%', :title, '%'))" +
            "and (tasks.description is null or tasks.description like concat('%', :description, '%'))" +
            "and (tasks.status is null or tasks.status like concat('%', :status, '%'))" +
            "and (tasks.priority is null or tasks.priority like concat('%', :priority, '%'))")
    List<Tuple> findTaskByExecutorId(Integer userId, String title, String description, String status, String priority);

    @Query(nativeQuery = true, value = "select tasks.id, tasks.title, tasks.description, tasks.status, tasks.priority, tasks.user_id_author, tasks.user_id_executor from management.tasks" +
            "         where (tasks.user_id_executor = :userId) " +
            "and (tasks.title is null or tasks.title like concat('%', :title, '%'))" +
            "and (tasks.description is null or tasks.description like concat('%', :description, '%'))" +
            "and (tasks.status is null or tasks.status like concat('%', :status, '%'))" +
            "and (tasks.priority is null or tasks.priority like concat('%', :priority, '%'))")
    Page<Tuple> findTaskByExecutorId(Integer userId, Pageable pageable, String title, String description, String status, String priority);

    @Query(nativeQuery = true, value = "select tasks.id, tasks.title, tasks.description, tasks.status, tasks.priority, tasks.user_id_author, tasks.user_id_executor from management.tasks" +
            "         where (tasks.user_id_author = :userId) " +
            "and (tasks.title is null or tasks.title like concat('%', :title, '%'))" +
            "and (tasks.description is null or tasks.description like concat('%', :description, '%'))" +
            "and (tasks.status is null or tasks.status like concat('%', :status, '%'))" +
            "and (tasks.priority is null or tasks.priority like concat('%', :priority, '%'))")
    List<Tuple> findTaskByAuthorId(Integer userId, String title, String description, String status, String priority);

    @Query(nativeQuery = true, value = "select tasks.id, tasks.title, tasks.description, tasks.status, tasks.priority, tasks.user_id_author, tasks.user_id_executor from management.tasks" +
            "         where (tasks.user_id_author = :userId) " +
            "and (tasks.title is null or tasks.title like concat('%', :title, '%'))" +
            "and (tasks.description is null or tasks.description like concat('%', :description, '%'))" +
            "and (tasks.status is null or tasks.status like concat('%', :status, '%'))" +
            "and (tasks.priority is null or tasks.priority like concat('%', :priority, '%'))")
    Page<Tuple> findTaskByAuthorId(Integer userId, Pageable pageable, String title, String description, String status, String priority);
}
