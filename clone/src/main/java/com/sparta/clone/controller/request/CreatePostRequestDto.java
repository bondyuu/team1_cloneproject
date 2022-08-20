package com.sparta.clone.controller.request;

import com.sparta.clone.domain.Post;
import com.sparta.clone.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreatePostRequestDto {

    @NotBlank

    private String title;
    @NotBlank
    private String content;

    private User user;

    private MultipartFile imgFile;


    public Post toPost(String imgUrl) {
        return Post.builder()
                .title(title)
                .user(user)
                .imageUrl(imgUrl)
                .content(content)
                .likestate(false)
                .likeCnt(0)
                .build();
    }

}
