package com.sungbin.blog.user.controller;

import com.sungbin.blog.user.service.AuthenticationService;
import com.sungbin.blog.user.service.CustomUserDetailService;
import com.sungbin.blog.user.domain.Users;
import com.sungbin.blog.user.dto.AuthReqDto;
import com.sungbin.blog.user.dto.AuthResDto;
import com.sungbin.blog.user.repository.UsersRepository;
import com.sungbin.blog.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public AuthResDto createAuthenticationToken(@RequestBody AuthReqDto req) {
        return authenticationService.createAuthenticationToken(req);
    }

    @PostMapping("/sign-up")
    public Users save(@RequestBody AuthReqDto req){
        return authenticationService.save(req);
    }
}