package com.muza.server.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "posttypeid", nullable = false)
    private Short postTypeId;

    @Column(name = "acceptedanswerid")
    private Integer acceptedAnswerId;

    @Column(name = "parentid")
    private Integer parentId;

    @CreationTimestamp
    @Column(name = "creationdate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "deletiondate")
    private LocalDateTime deletionDate;

    @Column(nullable = false)
    private Integer score = 0;

    @Column(name = "viewcount")
    private Integer viewCount;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(name = "owneruserid")
    private Integer ownerUserId;

    @Column(name = "ownerdisplayname", length = 40)
    private String ownerDisplayName;

    @Column(name = "lasteditoruserid")
    private Integer lastEditorUserId;

    @Column(name = "lasteditordisplayname", length = 40)
    private String lastEditorDisplayName;

    @Column(name = "lasteditdate")
    private LocalDateTime lastEditDate;

    @Column(name = "lastactivitydate")
    private LocalDateTime lastActivityDate;

    @Column(length = 250)
    private String title;

    @Column(length = 250)
    private String tags;

    @Column(name = "answercount")
    private Integer answerCount;

    @Column(name = "commentcount")
    private Integer commentCount;

    @Column(name = "favoritecount")
    private Integer favoriteCount;

    @Column(name = "closeddate")
    private LocalDateTime closedDate;

    @Column(name = "communityowneddate")
    private LocalDateTime communityOwnedDate;

    @Column(name = "contentlicense", length = 12)
    private String contentLicense;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owneruserid", insertable = false, updatable = false)
    private User ownerUser;

    @OneToMany
    @JoinColumn(name = "parentid", referencedColumnName = "id")
    private List<Post> answers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentid", insertable = false, updatable = false)
    private Post question;

    public List<String> getTagList() {
        if (tags == null || tags.isEmpty()) return new ArrayList<>();
        return List.of(tags.substring(1, tags.length() - 1).split("><"));
    }
}