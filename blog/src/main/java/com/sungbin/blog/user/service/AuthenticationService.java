package com.sungbin.blog.user.service;

import com.sungbin.blog.common.CustomException;
import com.sungbin.blog.user.domain.Users;
import com.sungbin.blog.user.dto.AuthReqDto;
import com.sungbin.blog.user.dto.AuthResDto;
import com.sungbin.blog.user.repository.UsersRepository;
import com.sungbin.blog.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

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
        System.out.println(userDetails.getAuthorities());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), (Optional<SimpleGrantedAuthority>) userDetails.getAuthorities().stream().findFirst());

        return new AuthResDto(jwt);
    }

    public Users save(AuthReqDto req){
        usersRepository.findByUsername(req.getUsername())
                .ifPresent(user -> {
                    throw new CustomException("Duplicate Username");
                });

        Users user = Users.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();
        return usersRepository.save(user);
    }
}
