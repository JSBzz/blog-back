package com.sungbin.blog.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private final String role = "USER";

    @Builder
    public Users (String username, String password){
        this.username = username;
        this.password = password;
    }

}
