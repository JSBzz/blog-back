package com.sungbin.blog.post.controller;

import com.sungbin.blog.post.domain.Comment;
import com.sungbin.blog.post.dto.CommentReqDto;
import com.sungbin.blog.post.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/comment")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(postCommentService.getComment(postId));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Comment> saveComment(@PathVariable("postId") Long postId, @RequestBody CommentReqDto req){
        return ResponseEntity.ok(postCommentService.saveComment(req, postId));
    }

}
