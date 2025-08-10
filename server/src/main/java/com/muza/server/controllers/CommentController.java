package com.muza.server.controllers;

import com.muza.server.dto.CommentDTO;
import com.muza.server.entities.Comment;
import com.muza.server.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.createComment(commentDTO);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        Comment updatedComment = commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Comment>> getCommentsByQuestion(@PathVariable Long questionId) {
        List<Comment> comments = commentService.getCommentsByQuestion(questionId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/answer/{answerId}")
    public ResponseEntity<List<Comment>> getCommentsByAnswer(@PathVariable Long answerId) {
        List<Comment> comments = commentService.getCommentsByAnswer(answerId);
        return ResponseEntity.ok(comments);
    }
}
