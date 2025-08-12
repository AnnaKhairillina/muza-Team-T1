package com.muza.server.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tags")
@Data
public class Tag {

    @Id
    private Integer id;

    @Column(name = "tagname", unique = true, nullable = false, length = 35)
    private String tagName;

    private Integer count;

    @Column(name = "excerptpostid")
    private Integer excerptPostId;

    @Column(name = "wikipostid")
    private Integer wikiPostId;

    @Column(name = "ismoderatoronly")
    private Boolean isModeratorOnly;

    @Column(name = "isrequired")
    private Boolean isRequired;
}