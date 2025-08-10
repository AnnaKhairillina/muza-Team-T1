package com.muza.server.dto;

import com.muza.server.entities.Question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionResponse {
    private Long id;
    private String title;
    private String body;
    private Integer score;
    private LocalDateTime createdAt;
    private UserResponse user;
    private List<AnswerResponse> answers;
    private List<String> tags;

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.body = question.getBody();
        this.score = question.getScore();
        this.createdAt = question.getCreatedAt();
        this.user = new UserResponse(question.getUser());
        this.answers = question.getAnswers().stream()
                .map(AnswerResponse::new)
                .collect(Collectors.toList());

        this.tags = question.getTags().stream()
                .map(tag -> tag.getName())
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Integer getScore() {
        return score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserResponse getUser() {
        return user;
    }

    public List<AnswerResponse> getAnswers() {
        return answers;
    }

    public List<String> getTags() {
        return tags;
    }  // Геттер для тегов
}
