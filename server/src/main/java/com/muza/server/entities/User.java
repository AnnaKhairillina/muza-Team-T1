package com.muza.server.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer reputation;

    @CreationTimestamp
    @Column(name = "creationdate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "displayname", length = 40, nullable = false)
    private String displayName;

    @Column(name = "lastaccessdate")
    private LocalDateTime lastAccessDate;

    @Column(name = "websiteurl", length = 200)
    private String websiteUrl;

    @Column(length = 100)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String aboutme;

    private Integer views;
    private Integer upvotes;
    private Integer downvotes;

    @Column(name = "profileimageurl", length = 200)
    private String profileImageUrl;

    @Column(name = "emailhash", length = 32)
    private String emailHash;

    @Column(name = "accountid")
    private Integer accountId;

    @OneToMany(mappedBy = "ownerUser", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Vote> votes;
}