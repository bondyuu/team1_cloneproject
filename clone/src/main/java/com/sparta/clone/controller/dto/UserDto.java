package com.sparta.clone.controller.dto;


import com.sparta.clone.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String introduction;
    private String profileUrl;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.introduction = user.getIntroduction();
        this.profileUrl = user.getProfileUrl();
    }
}
