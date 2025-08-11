package com.muza.server.services;

import com.muza.server.dto.VoteDTO;
import com.muza.server.entities.Answer;
import com.muza.server.entities.Question;
import com.muza.server.entities.User;
import com.muza.server.entities.Vote;
import com.muza.server.repositories.AnswerRepository;
import com.muza.server.repositories.QuestionRepository;
import com.muza.server.repositories.UserRepository;
import com.muza.server.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public void voteForQuestion(Long questionId, VoteDTO voteDTO) {
        User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Vote existingVote = voteRepository.findByUserAndQuestion(user, question).orElse(null);

        if (existingVote == null) {
            // Новый голос
            Vote newVote = new Vote();
            newVote.setUser(user);
            newVote.setQuestion(question);
            newVote.setAnswer(null);
            newVote.setValue(voteDTO.getValue());
            voteRepository.save(newVote);

            question.setVotesSum(safeSum(question.getVotesSum()) + voteDTO.getValue());
        } else {
            // Изменение голоса
            int oldValue = existingVote.getValue();
            int newValue = voteDTO.getValue();

            existingVote.setValue(newValue);
            voteRepository.save(existingVote);

            int delta = newValue - oldValue;
            question.setVotesSum(safeSum(question.getVotesSum()) + delta);
        }

        questionRepository.save(question);
    }

    @Transactional
    public void voteForAnswer(Long answerId, VoteDTO voteDTO) {
        User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        Vote existingVote = voteRepository.findByUserAndAnswer(user, answer).orElse(null);

        if (existingVote == null) {
            Vote newVote = new Vote();
            newVote.setUser(user);
            newVote.setAnswer(answer);
            newVote.setQuestion(null);
            newVote.setValue(voteDTO.getValue());
            voteRepository.save(newVote);

            answer.setVotesSum(safeSum(answer.getVotesSum()) + voteDTO.getValue());
        } else {
            int oldValue = existingVote.getValue();
            int newValue = voteDTO.getValue();

            existingVote.setValue(newValue);
            voteRepository.save(existingVote);

            int delta = newValue - oldValue;
            answer.setVotesSum(safeSum(answer.getVotesSum()) + delta);
        }

        answerRepository.save(answer);
    }

    private int safeSum(Integer sum) {
        return sum == null ? 0 : sum;
    }
}
