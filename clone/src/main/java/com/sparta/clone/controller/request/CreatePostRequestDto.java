package com.sparta.clone.controller.request;

import com.sparta.clone.domain.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreatePostRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private MultipartFile imageFile;

    public Post toPost(String imageUrl) {
        return Post.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .likestate(false)
                .likeCnt(0)
                .build();
    }

}
