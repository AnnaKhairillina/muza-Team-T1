package com.muza.server.dto;

import lombok.Data;

@Data
public class VoteDTO {
    private Integer userId;
    private int value;
}