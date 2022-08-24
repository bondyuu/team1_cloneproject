package com.sparta.clone.controller;


import com.amazonaws.Response;
import com.sparta.clone.controller.request.FollowRequestDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/api/follow/{userId}")
    public ResponseDto<?> follow(@PathVariable Long userId, @RequestBody FollowRequestDto requestDto, HttpServletRequest request) {
        return followService.follow(userId, requestDto, request);
    }

}
