package com.sungbin.blog.user.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.sungbin.blog.common.CustomException;
import com.sungbin.blog.user.domain.Users;
import com.sungbin.blog.user.dto.AuthReqDto;
import com.sungbin.blog.user.dto.AuthResDto;
import com.sungbin.blog.user.repository.UsersRepository;
import com.sungbin.blog.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailsService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

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

    public AuthResDto googleOauthLogin(Map<String, String> body) throws Exception {
        String code = body.get("code");

        // 1. code -> token 교환
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        Map<String, Object> tokenResponse = restTemplate.postForObject(
                "https://oauth2.googleapis.com/token", request, Map.class);

        String idToken = (String) tokenResponse.get("id_token");

        // 2. id_token 검증
        GoogleIdToken.Payload payload = verifyIdToken(idToken);
        String email = (String) payload.get("email");

        if (email == null) throw new CustomException("INVALID INFO");

        AtomicReference<Boolean> isNewUser = new AtomicReference<>(false);
        // 3. DB 조회 or 신규 가입
        Users user = usersRepository.findByUsername(email)
                .orElseGet(() -> {
                    isNewUser.set(true);
                    return new Users(email, null);
                });
        System.out.println("!!!!!!!!!");

        if (isNewUser.get()){
            usersRepository.save(user);
            System.out.println("???");
        }

        // 4. 자체 JWT 발급
        String jwt = jwtUtil.generateToken(email);
        return new AuthResDto(jwt);
    }

    private GoogleIdToken.Payload verifyIdToken(String idToken) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JSON_FACTORY)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken googleIdToken = verifier.verify(idToken);
        if (googleIdToken == null) throw new IllegalArgumentException("Invalid ID token");

        return googleIdToken.getPayload();
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
