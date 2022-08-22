package com.sparta.clone.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FollowResponseDto {
    private Boolean isFollow;
}
