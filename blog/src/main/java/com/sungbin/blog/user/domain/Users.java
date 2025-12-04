package com.sungbin.blog.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @ColumnDefault("'USER'")
    private String role;

    @Builder
    public Users (String username, String password){
        this.username = username;
        this.password = password;
    }

}
