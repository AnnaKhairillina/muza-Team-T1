package com.muza.server.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class Comment {

    @Id
    private Integer id;

    @Column(name = "postid", nullable = false)
    private Integer postId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "creationdate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "userid")
    private Integer userId;

    @Column(name = "contentlicense", length = 12)
    private String contentLicense;

    // Связи
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postid", insertable = false, updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private User user;
}