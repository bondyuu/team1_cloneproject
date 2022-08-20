package com.sparta.clone.repository;

import com.sparta.clone.domain.Post;
import com.sparta.clone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);
}
