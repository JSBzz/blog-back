package com.sungbin.blog.post.service;

import com.sungbin.blog.post.repository.PostRepository;
import com.sungbin.blog.post.domain.Post;
import com.sungbin.blog.post.dto.PostReqDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> getPosts(){
        Pageable pageable = PageRequest.of(0, 10);
        return postRepository.findAll(pageable);
    }

    public Post savePost(PostReqDto req){
        Post post = Post.builder()
                .title(req.getTitle())
                .contents(req.getContents())
                .build();
        return postRepository.save(post);
    }
}
