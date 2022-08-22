package com.sparta.clone.repository;

import com.sparta.clone.domain.Comment;
import com.sparta.clone.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
