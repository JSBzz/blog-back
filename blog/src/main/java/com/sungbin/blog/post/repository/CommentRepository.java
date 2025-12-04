package com.sungbin.blog.post.repository;

import com.sungbin.blog.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByDeleteYnAndPostId(String deleteYn, Long postId);
}
