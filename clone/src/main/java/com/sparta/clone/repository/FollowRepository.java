package com.sparta.clone.repository;

import com.sparta.clone.domain.Follow;
import com.sparta.clone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByUserTo(User user);
    List<Follow> findAllByUserFrom(User user);
    void deleteByUserFromAndUserTo(User userFrom, User userTo);

    Follow findByUserToAndUserFrom(User userTo, User userFrom);

}
