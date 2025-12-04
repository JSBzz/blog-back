package com.sungbin.blog.post.service;

import com.sungbin.blog.common.CustomException;
import com.sungbin.blog.post.domain.Comment;
import com.sungbin.blog.post.domain.Post;
import com.sungbin.blog.post.dto.CommentReqDto;
import com.sungbin.blog.post.dto.PostReqDto;
import com.sungbin.blog.post.repository.CommentRepository;
import com.sungbin.blog.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostCommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getComment(Long postId){
        return commentRepository.findByDeleteYnAndPostId("N", postId);
    }
//
//    public Post getPostDetail(Long id){
//        return postRepository.findById(id).orElseThrow(() -> new CustomException("POST NOT FOUND"));
//    }
//
    public Comment saveComment(CommentReqDto req, Long postId){
        Comment comment = Comment.builder()
                .content(req.getContent())
                .author(req.getAuthor())
                .postId(postId)
                .build();
        return commentRepository.save(comment);
    }
//
//    public Post updatePost(Long id, PostReqDto req){
//        Post post = Post.builder()
//                .id(req.getId())
//                .title(req.getTitle())
//                .contents(req.getContents())
//                .build();
//        return postRepository.save(post);
//    }
//
//    @Transactional
//    public Boolean deletePost(Long id){
//        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException("POST NOT FOUND"));
//        post.updateDeleteFlag("Y");
//        return true;
//    }
}
