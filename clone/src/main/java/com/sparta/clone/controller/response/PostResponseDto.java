package com.sparta.clone.controller.response;


import com.sparta.clone.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    private Long postId;
    private Long userId;
    private String username;
    private String profileUrl;
    private String postUrl;
    private Long likeCnt;
    private Boolean likeState;
    private List<CommentResponseDto> commentList;

}
