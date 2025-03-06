package com.example.management.service;

import com.example.management.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    /**
     * Создание комментария
     *
     * @return созданный комментарий
     */
    Comment create(Comment comment);

    /**
     * Получение всех комментариев по id задачи
     *
     * @return список комментариев
     */
    List<Comment> findCommentByTaskId(Integer taskId, Pageable pageable);
}
