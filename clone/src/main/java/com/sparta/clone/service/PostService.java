package com.sparta.clone.service;

import com.amazonaws.services.kms.model.CreateGrantRequest;
import com.sparta.clone.controller.request.CreateHeartRequestDto;
import com.sparta.clone.controller.request.CreatePostRequestDto;
import com.sparta.clone.controller.request.PostRequestDto;
import com.sparta.clone.controller.response.CommentResponseDto;
import com.sparta.clone.controller.response.LikeResponseDto;
import com.sparta.clone.controller.response.PostResponseDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.domain.Heart;
import com.sparta.clone.domain.Post;
import com.sparta.clone.domain.User;
import com.sparta.clone.domain.UserDetailsImpl;
import com.sparta.clone.repository.CommentRepository;
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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final S3UploadService s3UploadService;
    private final PostRepository postRepository;

    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;

    //포스트 생성
    public ResponseDto<?> createpost(CreatePostRequestDto postRequestDto) throws IOException {
        String imgUrl = s3UploadService.upload(postRequestDto.getImgFile(),"static");
        return ResponseDto.success(postRepository.save(postRequestDto.toPost(imgUrl)));
    }

    //포스트 전체 조회
    public ResponseDto<?> getallpost(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Post> postList = postRepository.findAll();
//        postList.forEach(post -> post.setCommentList(commentRepository.findAllByPost(post)));
        return ResponseDto.success(postList.stream()
                .map(post -> PostResponseDto.builder()
                        .postId(post.getId())
                        .userId(post.getUser().getUserId())
                        .username(post.getUser().getUsername())
                        .profileUrl(post.getUser().getImgUrl())
                        .likeCnt((long)post.getLikeCnt())
                        .postUrl(post.getImageUrl())
                        .likeState(likestate(post,user))
                        .commentList(commentRepository.findAllByPost(post).stream()
                                .map(comment -> CommentResponseDto.builder()
                                        .commentId(comment.getCommentId())
                                        .userId(comment.getUser().getUserId())
                                        .username(comment.getUser().getUsername())
                                        .imgUrl(comment.getUser().getImgUrl())
                                        .comment(comment.getComment())
                                        .build())
                                .collect(Collectors.toList()))
                        .build()));
    }

    //포스트 삭제
    public ResponseDto<?> deletepost(Long postid) {
        postRepository.deleteById(postid);
        return ResponseDto.success(postid);
    }

    public ResponseDto<?> likepost(Long postid, UserDetailsImpl userDetails) {

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

        return ResponseDto.success(
                LikeResponseDto.builder()
                        .likestate(likestate)
                        .likecnt(post.getLikeCnt())
                        .build()
        );
    }

    public ResponseDto<?> getdetailpost(long postid, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Post post = postRepository.findById(postid).orElseThrow(() -> new RuntimeException("해당하는 포스팅없음"));


        return ResponseDto.success(PostResponseDto.builder()
                .postId(post.getId())
                .userId(post.getUser().getUserId())
                .username(post.getUser().getUsername())
                .profileUrl(post.getUser().getImgUrl())
                .likeCnt((long)post.getLikeCnt())
                .postUrl(post.getImageUrl())
                .likeState(likestate(post,user))
                .commentList(commentRepository.findAllByPost(post).stream()
                                                                  .map(comment -> CommentResponseDto.builder()
                                                                                                    .commentId(comment.getCommentId())
                                                                                                    .userId(comment.getUser().getUserId())
                                                                                                    .username(comment.getUser().getUsername())
                                                                                                    .imgUrl(comment.getUser().getImgUrl())
                                                                                                    .comment(comment.getComment())
                                                                                                    .build())
                                                                  .collect(Collectors.toList()))
                .build());
    }

    public Boolean likestate(Post post, User user) {
        Heart heart = heartRepository.findByPostAndAndLikeuserid(post,user.getUserId());
        return heart != null;
    }

}
