package com.muza.server.dto;

import com.muza.server.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Integer id;
    private String displayName;
    private String emailHash;
    private LocalDateTime creationDate;
    private Integer reputation;

    public UserResponse(User user) {
        this.id = user.getId();
        this.displayName = user.getDisplayName();
        this.emailHash = user.getEmailHash();
        this.creationDate = user.getCreationDate();
        this.reputation = user.getReputation();
    }
}