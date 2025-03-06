package com.example.management.repository;

import com.example.management.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentByTaskId (Integer id, Pageable pageable);
    List<Comment> findCommentByTaskId (Integer id);
}
