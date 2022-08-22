package com.sparta.clone.repository;

import com.sparta.clone.domain.Heart;
import com.sparta.clone.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart findByPostAndAndLikeuserid(Post post, Long userid);

    List<Heart> findAllByLikeuserid(Long userid);

}
