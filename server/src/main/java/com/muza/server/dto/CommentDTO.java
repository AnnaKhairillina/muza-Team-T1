package com.muza.server.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private String body;
    private Integer userId;
    private Integer postId;
}