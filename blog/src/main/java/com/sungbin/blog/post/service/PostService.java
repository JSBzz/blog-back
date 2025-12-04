package com.sungbin.blog.post.service;

import com.sungbin.blog.common.CustomException;
import com.sungbin.blog.post.repository.PostRepository;
import com.sungbin.blog.post.domain.Post;
import com.sungbin.blog.post.dto.PostReqDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> getPosts(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public Post getPostDetail(Long id){
        return postRepository.findById(id).orElseThrow(() -> new CustomException("POST NOT FOUND"));
    }

    public Post savePost(PostReqDto req){
        Post post = Post.builder()
                .title(req.getTitle())
                .contents(req.getContents())
                .build();
        return postRepository.save(post);
    }

    public Post updatePost(Long id, PostReqDto req){
        Post post = Post.builder()
                .id(req.getId())
                .title(req.getTitle())
                .contents(req.getContents())
                .build();
        return postRepository.save(post);
    }

    @Transactional
    public Post deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException("POST NOT FOUND"));
        post.updateDeleteFlag("Y");
        return post;
    }
}
