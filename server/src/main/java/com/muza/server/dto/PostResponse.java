package com.muza.server.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private Integer id;
    private Short postTypeId;
    private String title;
    private String body;
    private Integer score;
    private LocalDateTime creationDate;
    private Integer userId;
    private String username;
    private List<String> tags;
    private Integer parentId;
}