package com.muza.server.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@Data
public class Vote {

    @Id
    private Integer id;

    @Column(name = "postid", nullable = false)
    private Integer postId;

    @Column(name = "votetypeid", nullable = false)
    private Short voteTypeId;

    @Column(name = "userid")
    private Integer userId;

    @Column(name = "creationdate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "bountyamount")
    private Integer bountyAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postid", insertable = false, updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private User user;
}