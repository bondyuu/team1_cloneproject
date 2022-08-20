package com.sparta.clone.repository;

import com.sparta.clone.domain.Heart;
import com.sparta.clone.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart findByPostAndAndLikeuserid(Post post, Long userid);

}
