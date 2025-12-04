package com.sungbin.blog.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthReqDto {
    
    @NotBlank(message = "username은 필수값입니다.")
    private String username;

    @NotBlank(message = "password는 필수값입니다.")
    private String password;

}
