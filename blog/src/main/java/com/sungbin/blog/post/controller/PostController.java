package com.sungbin.blog.post.controller;

import com.sungbin.blog.post.service.PostService;
import com.sungbin.blog.post.domain.Post;
import com.sungbin.blog.post.dto.PostReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<Post>> getPosts(@AuthenticationPrincipal UserDetails user){
        System.out.println(user.getUsername());
        return ResponseEntity.ok(postService.getPosts());
    }

    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody PostReqDto req){
        return ResponseEntity.ok(postService.savePost(req));
    }
}
