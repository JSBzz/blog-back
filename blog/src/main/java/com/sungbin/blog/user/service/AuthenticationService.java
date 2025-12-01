package com.sungbin.blog.user.service;

import com.sungbin.blog.user.domain.Users;
import com.sungbin.blog.user.dto.AuthReqDto;
import com.sungbin.blog.user.dto.AuthResDto;
import com.sungbin.blog.user.repository.UsersRepository;
import com.sungbin.blog.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailsService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AuthResDto createAuthenticationToken(AuthReqDto req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthResDto(jwt);
    }

    public Users save(AuthReqDto req){
        Users user = Users.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();
        return usersRepository.save(user);
    }
}
