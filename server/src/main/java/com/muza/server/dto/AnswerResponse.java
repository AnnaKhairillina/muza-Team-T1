package com.muza.server.dto;

import com.muza.server.entities.Answer;

import java.time.LocalDateTime;

public class AnswerResponse {
    private Long id;
    private String body;
    private Integer score;
    private boolean accepted;
    private LocalDateTime createdAt;
    private Long userId;
    private String username;

    public AnswerResponse(Answer answer) {
        this.id = answer.getId();
        this.body = answer.getBody();
        this.score = answer.getVotesSum();
        this.accepted = answer.isAccepted();
        this.createdAt = answer.getCreatedAt();
        this.userId = answer.getUser().getId();
        this.username = answer.getUser().getUsername();
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Integer getScore() {
        return score;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}