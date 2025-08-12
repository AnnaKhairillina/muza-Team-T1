package com.muza.server.repositories;

import com.muza.server.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByPostId(Integer postId);

    List<Comment> findByUserId(Integer userId);
}