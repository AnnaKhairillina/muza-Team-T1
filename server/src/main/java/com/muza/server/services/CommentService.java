package com.muza.server.services;

import com.muza.server.dto.CommentDTO;
import com.muza.server.entities.Comment;
import com.muza.server.entities.Post;
import com.muza.server.entities.User;
import com.muza.server.repositories.CommentRepository;
import com.muza.server.repositories.PostRepository;
import com.muza.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment createComment(CommentDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setText(dto.getBody());
        comment.setUserId(user.getId());
        comment.setPostId(post.getId());
        comment.setCreationDate(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Integer id, CommentDTO dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setText(dto.getBody());
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPost(Integer postId) {
        return commentRepository.findByPostId(postId);
    }
}