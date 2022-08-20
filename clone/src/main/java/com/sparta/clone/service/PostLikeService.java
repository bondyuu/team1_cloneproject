package com.sparta.clone.service;

import com.sparta.clone.controller.request.PostLikeRequestDto;
import com.sparta.clone.controller.response.PostLikeResponseDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.domain.Post;
import com.sparta.clone.domain.PostLike;
import com.sparta.clone.domain.User;
import com.sparta.clone.global.error.ErrorCode;
import com.sparta.clone.jwt.TokenProvider;
import com.sparta.clone.repository.PostLikeRepository;
import com.sparta.clone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final TokenProvider tokenProvider;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseDto<?> heart(Long postId, PostLikeRequestDto requestDto, HttpServletRequest request) {
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

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseDto.fail(ErrorCode.POST_NOT_FOUND);
        }

        if(requestDto.getLikeState() == 0 ) {
            postLikeRepository.save(PostLike.builder()
                    .user(user)
                    .post(post)
                    .build());

            return ResponseDto.success(PostLikeResponseDto.builder()
                    .isHeart(true)
                    .likeCnt(postLikeRepository.countAllByPost(post))
                    .build());
        } else {
            postLikeRepository.delete(PostLike.builder()
                    .user(user)
                    .post(post)
                    .build());

            return ResponseDto.success(PostLikeResponseDto.builder()
                    .isHeart(false)
                    .likeCnt(postLikeRepository.countAllByPost(post))
                    .build());
        }
    }


    public User validateUser(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getUserFromAuthentication();
    }
}
