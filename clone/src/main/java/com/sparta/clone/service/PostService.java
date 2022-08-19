package com.sparta.clone.service;

import com.sparta.clone.controller.request.CreatePostRequestDto;
import com.sparta.clone.controller.request.PostRequestDto;
import com.sparta.clone.domain.Post;
import com.sparta.clone.repository.PostRepository;
import com.sparta.clone.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final S3UploadService s3UploadService;
    private final PostRepository postRepository;

    public Post createpost(CreatePostRequestDto postRequestDto, MultipartFile imageFIle) throws IOException {
        String imageurl = s3UploadService.upload(imageFIle,"static");
        postRequestDto.setImgUrl(imageurl);
        System.out.println(imageurl);
        return postRepository.save(postRequestDto.toPost());
    }


    public List<Post> getallpost() {
        return postRepository.findAll();
    }
}
