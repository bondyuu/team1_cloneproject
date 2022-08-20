package com.sparta.clone.controller.dto;


import com.sparta.clone.domain.Comment;
import com.sparta.clone.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private String imageUrl;

    public PostDto(Post post) {
        this.postId = post.getId();
        this.imageUrl = post.getImageUrl();
    }

}
