package com.muza.server.services;

import com.muza.server.entities.Answer;
import com.muza.server.entities.Question;
import com.muza.server.entities.User;
import com.muza.server.repositories.AnswerRepository;
import com.muza.server.repositories.QuestionRepository;
import com.muza.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.muza.server.dto.AnswerResponse;
import java.util.Optional;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public AnswerResponse createAnswer(Answer answer, Long questionId, Long userId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        answer.setQuestion(question);
        answer.setUser(user);
        Answer savedAnswer = answerRepository.save(answer);
        return new AnswerResponse(savedAnswer);
    }
    @Transactional(readOnly = true)
    public Optional<AnswerResponse> getAnswerById(Long id) {
        return answerRepository.findById(id)
                .map(AnswerResponse::new);
    }

    @Transactional(readOnly = true)
    public List<AnswerResponse> getAllAnswers() {
        return answerRepository.findAll()
                .stream()
                .map(AnswerResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AnswerResponse updateAnswer(Long id, Answer updated) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
        answer.setBody(updated.getBody());
        answer.setAccepted(updated.isAccepted());
        answer.setScore(updated.getScore());
        Answer saved = answerRepository.save(answer);
        return new AnswerResponse(saved);
    }

    @Transactional
    public void deleteAnswer(Long id) {
        if (!answerRepository.existsById(id)) {
            throw new RuntimeException("Answer not found");
        }
        answerRepository.deleteById(id);
    }

}