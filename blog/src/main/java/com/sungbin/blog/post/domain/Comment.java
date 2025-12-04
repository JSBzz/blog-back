package com.sungbin.blog.post.domain;

import com.sungbin.blog.common.CommonDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends CommonDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String author;
    private Long postId;

    @Builder
    public Comment(Long id, String content, String author, Long postId) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.postId = postId;
    }

    @Builder
    public Comment(String content, String author, Long postId) {
        this.content = content;
        this.author = author;
        this.postId = postId;
    }
}