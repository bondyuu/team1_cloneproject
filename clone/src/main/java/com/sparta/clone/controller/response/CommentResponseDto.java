package com.sparta.clone.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentResponseDto {
    private Long commentId;
    private Long userId;
    private String username;
    private String imgUrl;
    private String comment;

}
