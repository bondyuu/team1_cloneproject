package com.sparta.clone.controller;

import com.sparta.clone.controller.request.CreatePostRequestDto;
import com.sparta.clone.domain.Post;
import com.sparta.clone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public Post createpost(CreatePostRequestDto postRequestDto) throws IOException {
        return postService.createpost(postRequestDto);
    }





}
