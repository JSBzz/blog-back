package com.sungbin.blog.user.repository;

import com.sungbin.blog.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    public Optional<Users> findByUsername(String username);
}
