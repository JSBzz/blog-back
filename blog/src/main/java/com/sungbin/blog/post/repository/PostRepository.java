package com.sungbin.blog.post.repository;

import com.sungbin.blog.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByDeleteYn(String deleteYn, Pageable pageable);
}
