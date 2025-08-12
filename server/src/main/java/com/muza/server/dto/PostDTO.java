package com.muza.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private Short postTypeId;
    private String title;
    private String body;
    private Integer userId;
    private Integer parentId;
    private List<String> tags;
}