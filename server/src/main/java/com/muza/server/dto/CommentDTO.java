package com.muza.server.dto;

public class CommentDTO {
    private String body;
    private Long userId;

    private Long questionId;
    private Long answerId;

    public CommentDTO() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
        if (questionId != null) {
            this.answerId = null;
        }
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
        if (answerId != null) {
            this.questionId = null;
        }
    }
}
