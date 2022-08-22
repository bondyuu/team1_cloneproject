package com.sparta.clone.controller.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponseDto {
    private int likecnt;
    private Boolean likestate;
}
