package com.sungbin.blog.post.dto;

import com.sungbin.blog.common.CommonDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostReqDto extends CommonDto {
    private String id;
    private String contents;
    private String title;
}
