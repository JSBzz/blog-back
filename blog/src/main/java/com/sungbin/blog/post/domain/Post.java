package com.sungbin.blog.post.domain;

import com.sungbin.blog.common.CommonDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends CommonDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;
    private String title;

    @Builder
    public Post(String contents, String title){
        this.contents = contents;
        this.title = title;
    }

}
