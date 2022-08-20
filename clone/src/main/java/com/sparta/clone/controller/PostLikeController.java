package com.sparta.clone.controller;

import com.sparta.clone.controller.request.PostLikeRequestDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/api/posts/like/{postId}")
    public ResponseDto<?> heart(@PathVariable Long postId, @RequestBody PostLikeRequestDto requestDto, HttpServletRequest request) {
        return postLikeService.heart(postId, requestDto, request);
    }
}
