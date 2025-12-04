package com.sungbin.blog.post.domain;

import com.sungbin.blog.common.CommonDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends CommonDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String contents;
    private String title;


    @Builder
    public Post(Long id, String contents, String title){
        this.id = id;
        this.contents = contents;
        this.title = title;
    }
    @Builder
    public Post(String contents, String title){
        this.contents = contents;
        this.title = title;
    }



}
