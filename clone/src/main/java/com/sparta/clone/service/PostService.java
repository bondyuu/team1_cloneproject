package com.sparta.clone.service;

import com.amazonaws.services.kms.model.CreateGrantRequest;
import com.sparta.clone.controller.request.CreateHeartRequestDto;
import com.sparta.clone.controller.request.CreatePostRequestDto;
import com.sparta.clone.controller.request.PostRequestDto;
import com.sparta.clone.controller.response.LikeResponseDto;
import com.sparta.clone.domain.Heart;
import com.sparta.clone.domain.Post;
import com.sparta.clone.domain.UserDetailsImpl;
import com.sparta.clone.repository.HeartRepository;
import com.sparta.clone.repository.PostRepository;
import com.sparta.clone.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class PostService {

    private final S3UploadService s3UploadService;
    private final PostRepository postRepository;

    private final HeartRepository heartRepository;

    //포스트 생성
    public Post createpost(CreatePostRequestDto postRequestDto) throws IOException {
        String imgUrl = s3UploadService.upload(postRequestDto.getImgFile(),"static");
        System.out.println(imgUrl);
        return postRepository.save(postRequestDto.toPost(imgUrl));
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

    public LikeResponseDto likepost(Long postid, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new RuntimeException("해당하는 포스팅이 없다"));
        Long userid = userDetails.getUser().getUserId();

        CreateHeartRequestDto createHeartRequestDto = new CreateHeartRequestDto();
        createHeartRequestDto.setUserid(userid);
        createHeartRequestDto.setPost(post);

        Boolean likestate;

        Heart heart = heartRepository.findByPostAndAndLikeuserid(post,userid);

        //좋아요 취소
        if(post.getHeartList().contains(heart)) {
            post.dislike();
            post.getHeartList().remove(heart);
            heartRepository.delete(heart);
            likestate = false;
        }

        //좋아요
        else {
            post.like();
            heart = new Heart(createHeartRequestDto);
            heartRepository.save(heart);
            heart.confirmPost(post);
            likestate = true;
        }

        return LikeResponseDto.builder().likestate(likestate).likecnt(post.getLikeCnt()).build();

    }
}
