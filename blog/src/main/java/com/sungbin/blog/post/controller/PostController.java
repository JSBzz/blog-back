package com.sungbin.blog.post.controller;

import com.sungbin.blog.post.service.PostService;
import com.sungbin.blog.post.domain.Post;
import com.sungbin.blog.post.dto.PostReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<Post>> getPosts(Pageable pageable){
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostDetail(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getPostDetail(id));
    }

    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody PostReqDto req){
        return ResponseEntity.ok(postService.savePost(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") Long id, @RequestBody PostReqDto req){
        return ResponseEntity.ok(postService.updatePost(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.deletePost(id));
    }
}
