package com.muza.server.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "postlinks")
@Data
public class PostLink {

    @Id
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime creationdate;

    @Column(name = "postid", nullable = false)
    private Integer postId;

    @Column(name = "relatedpostid", nullable = false)
    private Integer relatedPostId;

    @Column(name = "linktypeid", nullable = false)
    private Short linkTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postid", insertable = false, updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relatedpostid", insertable = false, updatable = false)
    private Post relatedPost;
}