package com.sparta.clone.controller;

import com.sparta.clone.controller.request.CreatePostRequestDto;
import com.sparta.clone.domain.Post;
import com.sparta.clone.domain.UserDetailsImpl;
import com.sparta.clone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public Post createpost(@RequestPart(value = "request", required = false) CreatePostRequestDto postRequestDto, @RequestPart(value = "file", required = false)MultipartFile imgFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        postRequestDto.setUser(userDetails.getUser());
        return postService.createpost(postRequestDto,imgFile);
    }

    @GetMapping("")
    public List<Post> getallpost() {
        return postService.getallpost();
    }

    @DeleteMapping("/{postid}")
    public Long deletepost(@PathVariable Long postid) {
        return postService.deletepost(postid);
    }




}
