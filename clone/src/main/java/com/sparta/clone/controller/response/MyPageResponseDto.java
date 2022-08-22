package com.sparta.clone.controller.response;

import com.sparta.clone.controller.dto.PostDto;
import com.sparta.clone.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class MyPageResponseDto {
    private Long userId;
    private String username;
    private String profileUrl;
    private String introduction;
    private Long postCnt;
    private Long follower;
    private Long following;
    private List<PostDto> postList;
    private List<PostDto> likePostList;
}
