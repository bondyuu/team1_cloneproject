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


    //포스트 생성
    public Post createpost(CreatePostRequestDto postRequestDto, MultipartFile imageFIle) throws IOException {
        String imageurl = s3UploadService.upload(imageFIle,"static");
        postRequestDto.setImgUrl(imageurl);
        System.out.println(imageurl);
        return postRepository.save(postRequestDto.toPost());
    }

    //포스트 전체 조회
    public List<Post> getallpost() {
        return postRepository.findAll();
    }

    //포스트 삭제
    public Long deletepost(Long postid) {
        postRepository.deleteById(postid);
        return postid;
    }
}
