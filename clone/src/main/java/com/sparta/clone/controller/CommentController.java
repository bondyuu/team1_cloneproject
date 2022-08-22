package com.sparta.clone.controller;


import com.sparta.clone.controller.request.CommentRequestDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseDto<?> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.create(postId, requestDto, request);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId, HttpServletRequest request) {

        return commentService.delete(commentId, request);
    }
}
