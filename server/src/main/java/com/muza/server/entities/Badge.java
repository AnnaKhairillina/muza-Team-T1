package com.muza.server.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "badges")
@Data
public class Badge {

    @Id
    private Integer id;

    @Column(name = "userid", nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "class")
    private Short badgeClass;

    // Связь
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private User user;
}