package com.sparta.clone.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeResponseDto {
    private Boolean isHeart;
    private Long likeCnt;
}
