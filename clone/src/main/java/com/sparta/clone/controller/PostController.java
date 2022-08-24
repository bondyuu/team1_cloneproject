package com.sparta.clone.controller;

import com.sparta.clone.controller.request.CreatePostRequestDto;
import com.sparta.clone.controller.response.LikeResponseDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.domain.Post;
import com.sparta.clone.domain.UserDetailsImpl;
import com.sparta.clone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseDto<?> createpost(CreatePostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        postRequestDto.setUser(userDetails.getUser());
        return postService.createpost(postRequestDto);
    }


    @GetMapping("")
    public ResponseDto<?> getallpost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getallpost(userDetails);
    }

    @GetMapping("/{postid}")
    public ResponseDto<?> getdetailpost(@PathVariable long postid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getdetailpost(postid, userDetails);
    }


    @DeleteMapping("/{postid}")
    public ResponseDto<?> deletepost(@PathVariable Long postid,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletepost(postid,userDetails);
    }

    @PostMapping("/like/{postid}")
    public ResponseDto<?> likepost(@PathVariable Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails ) {
        return postService.likepost(postid,userDetails);
    }



}
