package com.sparta.clone.controller.request;

import com.sparta.clone.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateHeartRequestDto {

    private Long userid;

    private Post post;
}
