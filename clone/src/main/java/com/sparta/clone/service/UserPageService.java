package com.sparta.clone.service;

import com.sparta.clone.controller.dto.PostDto;
import com.sparta.clone.controller.response.MyPageResponseDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.controller.response.UserPageResponseDto;
import com.sparta.clone.domain.*;
import com.sparta.clone.global.error.ErrorCode;
import com.sparta.clone.jwt.TokenProvider;
import com.sparta.clone.repository.FollowRepository;
import com.sparta.clone.repository.HeartRepository;
import com.sparta.clone.repository.PostRepository;
import com.sparta.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPageService {
    private final TokenProvider tokenProvider;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final FollowService followService;

    public ResponseDto<?> getUserPage(Long userId, UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException(String.valueOf(ErrorCode.USER_NOT_FOUND))
        );

        //해당 유저가 작성한 게시글
        List<PostDto> postList = getPostList(user);

        //해당 유저가 좋아요한 게시글
        List<PostDto> likePostList = getLikePostList(user);

        Long postCnt = (long) postList.size();
        Long follower = (long)followRepository.findAllByUserFrom(user).size();
        Long following = (long)followRepository.findAllByUserTo(user).size();

        return ResponseDto.success(UserPageResponseDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .profileUrl(user.getImgUrl())
                .introduction(user.getIntroduction())
                .folloewState(followService.getFollowState(user,loginUser))
                .follower(follower)
                .following(following)
                .postCnt(postCnt)
                .postList(postList)
                .likePostList(likePostList)
                .build()
        );

    }

    public List<PostDto> getPostList(User user) {
        List<Post> postList = postRepository.findAllByUser(user);

        List<PostDto> postListDto = postList.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
        return postListDto;
    }

    public List<PostDto> getLikePostList(User user) {
        List<Heart> heartList = heartRepository.findAllByLikeuserid(user.getUserId());

        List<Post> likePostList = new ArrayList<>();
        for(Heart heart : heartList) {
            Post post = heart.getPost();

            likePostList.add(post);
        }
        return likePostList.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
    }

}
