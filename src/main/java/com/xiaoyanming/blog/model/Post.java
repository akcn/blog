package com.xiaoyanming.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "posts")
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Lob @Column(nullable = false)
    private String body;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("posts")
    private User author;

    @Column(nullable = false)
    private Date date = new Date();

    public Post(Long id, String title, String body, User author) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.author = author;
    }
}