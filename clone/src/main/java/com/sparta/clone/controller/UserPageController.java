package com.sparta.clone.controller;


import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserPageController {

    private final UserPageService userPageService;

    @GetMapping("api/detail/{userId}")
    public ResponseDto<?> getUserPage(@PathVariable Long userId) {
        return userPageService.getUserPage(userId);
    }
}
