package com.muza.server.dto;

public class VoteDTO {
    private Long userId;
    private int value; // +1 или -1

    public VoteDTO() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
