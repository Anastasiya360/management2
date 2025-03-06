package com.example.management.controller.impl;

import com.example.management.controller.CommentController;
import com.example.management.entity.Comment;
import com.example.management.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Component
@RequiredArgsConstructor
public class CommentControllerImpl implements CommentController {

    private final CommentService commentService;

    @Override
    public Comment create(Comment comment) {
        return commentService.create(comment);
    }

    @Override
    public List<Comment> findCommentByTaskId(Integer id, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            return commentService.findCommentByTaskId(id, null);
        }
        return commentService.findCommentByTaskId(id, PageRequest.of(pageNum, pageSize));
    }
}
