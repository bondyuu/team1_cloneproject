package com.sparta.clone.service;

import com.sparta.clone.controller.request.CreatePostRequestDto;
import com.sparta.clone.controller.request.PostRequestDto;
import com.sparta.clone.domain.Post;
import com.sparta.clone.repository.PostRepository;
import com.sparta.clone.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class PostService {

    private final S3UploadService s3UploadService;

    private final PostRepository postRepository;

    public Post createpost(CreatePostRequestDto postRequestDto) throws IOException {
        String imageurl = s3UploadService.upload(postRequestDto.getImageFile(),"static");
        return postRepository.save(postRequestDto.toPost(imageurl));
    }



}
