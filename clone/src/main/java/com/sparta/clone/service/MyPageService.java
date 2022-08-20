package com.sparta.clone.service;

import com.sparta.clone.controller.dto.PostDto;
import com.sparta.clone.controller.response.MyPageResponseDto;
import com.sparta.clone.controller.response.PostLikeResponseDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.domain.Post;
import com.sparta.clone.domain.PostLike;
import com.sparta.clone.domain.User;
import com.sparta.clone.global.error.ErrorCode;
import com.sparta.clone.jwt.TokenProvider;
import com.sparta.clone.repository.FollowRepository;
import com.sparta.clone.repository.PostLikeRepository;
import com.sparta.clone.repository.PostRepository;
import jdk.nashorn.internal.parser.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final TokenProvider tokenProvider;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public ResponseDto<?> getMyPage(HttpServletRequest request) {
        if(request.getHeader("Refresh-Token")==null) {
            return ResponseDto.fail(ErrorCode.LOGIN_REQUIRED);
        }
        if(request.getHeader("Authorization") == null) {
            return ResponseDto.fail(ErrorCode.LOGIN_REQUIRED);
        }

        User user = validateUser(request);
        if (null == user) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        //내가 작성한 게시글
        List<PostDto> postList = getPostList(user);

        //내가 좋아요한 게시글
        List<PostDto> likePostList = getLikePostList(user);

        Long postCnt = (long) postList.size();
        Long follower = (long)followRepository.findAllByUsernameFrom(user.getUsername()).size();
        Long following = (long)followRepository.findAllByUsernameTo(user.getUsername()).size();

        return ResponseDto.success(MyPageResponseDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .profileUrl(user.getProfileUrl())
                .introduction(user.getIntroduction())
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
        List<PostLike> postLikeList = postLikeRepository.findAllByUser(user);

        List<Post> likePostList = new ArrayList<>();
        for(PostLike postLike : postLikeList) {
            Post post = postLike.getPost();

            likePostList.add(post);
        }
        return likePostList.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
    }

    public User validateUser(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getUserFromAuthentication();
    }
}
