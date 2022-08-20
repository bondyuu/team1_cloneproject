package com.sparta.clone.repository;

import com.sparta.clone.domain.Follow;
import com.sparta.clone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByUsernameTo(String username);
    List<Follow> findAllByUsernameFrom(String username);
    void deleteByUsernameFromAndUsernameTo(String usernameFrom, String usernameTo);

}
