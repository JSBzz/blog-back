package com.sungbin.blog.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentReqDto {
    private Long id;
    private String content;
    private String author;
    private Long postId;
}
