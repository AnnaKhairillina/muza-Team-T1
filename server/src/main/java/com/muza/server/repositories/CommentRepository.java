package com.muza.server.repositories;

import com.muza.server.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByQuestionId(Long questionId);

    List<Comment> findByAnswerId(Long answerId);
}
