package com.muza.server.services;

import com.muza.server.dto.QuestionDTO;
import com.muza.server.dto.QuestionResponse;
import com.muza.server.entities.Question;
import com.muza.server.entities.Tag;
import com.muza.server.entities.User;
import com.muza.server.repositories.QuestionRepository;
import com.muza.server.repositories.TagRepository;
import com.muza.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional
    public QuestionResponse createQuestion(QuestionDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Question question = new Question();
        question.setTitle(dto.getTitle());
        question.setBody(dto.getBody());
        question.setUser(user);

        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            Set<Tag> managedTags = new HashSet<>();
            for (String tagName : dto.getTags()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return tagRepository.save(newTag);
                        });
                managedTags.add(tag);
            }
            question.setTags(managedTags);
        }

        return new QuestionResponse(questionRepository.save(question));
    }

    @Transactional(readOnly = true)
    public Optional<QuestionResponse> getQuestionById(Long id) {
        return questionRepository.findById(id)
                .map(QuestionResponse::new);
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponse> getAllQuestions(Pageable pageable, String tag) {
        if (tag != null && !tag.isEmpty()) {
            return questionRepository.findByTagName(tag, pageable)
                    .map(QuestionResponse::new);
        }
        return questionRepository.findAll(pageable)
                .map(QuestionResponse::new);
    }

    @Transactional
    public QuestionResponse updateQuestion(Long id, QuestionDTO dto) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        existingQuestion.setTitle(dto.getTitle());
        existingQuestion.setBody(dto.getBody());

        if (dto.getTags() != null) {
            Set<Tag> managedTags = new HashSet<>();
            for (String tagName : dto.getTags()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return tagRepository.save(newTag);
                        });
                managedTags.add(tag);
            }
            existingQuestion.setTags(managedTags);
        }

        return new QuestionResponse(questionRepository.save(existingQuestion));
    }

    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question not found");
        }
        questionRepository.deleteById(id);
    }
}
