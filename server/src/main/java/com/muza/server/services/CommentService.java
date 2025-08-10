package com.muza.server.services;

import com.muza.server.dto.CommentDTO;
import com.muza.server.entities.Answer;
import com.muza.server.entities.Comment;
import com.muza.server.entities.Question;
import com.muza.server.entities.User;
import com.muza.server.repositories.AnswerRepository;
import com.muza.server.repositories.CommentRepository;
import com.muza.server.repositories.QuestionRepository;
import com.muza.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public Comment createComment(CommentDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setBody(dto.getBody());
        comment.setUser(user);

        if (dto.getQuestionId() != null) {
            Question question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            comment.setQuestion(question);
        } else if (dto.getAnswerId() != null) {
            Answer answer = answerRepository.findById(dto.getAnswerId())
                    .orElseThrow(() -> new RuntimeException("Answer not found"));
            comment.setAnswer(answer);
        } else {
            throw new RuntimeException("Either questionId or answerId must be provided");
        }

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long id, CommentDTO dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setBody(dto.getBody());
        // Можно добавить логику смены привязки к вопросу/ответу, если нужно

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found");
        }
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByQuestion(Long questionId) {
        return commentRepository.findByQuestionId(questionId);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByAnswer(Long answerId) {
        return commentRepository.findByAnswerId(answerId);
    }
}
