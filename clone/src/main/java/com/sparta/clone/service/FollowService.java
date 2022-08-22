package com.sparta.clone.service;

import com.amazonaws.Response;
import com.sparta.clone.controller.request.FollowRequestDto;
import com.sparta.clone.controller.response.FollowResponseDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.domain.Follow;
import com.sparta.clone.domain.User;
import com.sparta.clone.global.error.ErrorCode;
import com.sparta.clone.jwt.TokenProvider;
import com.sparta.clone.repository.FollowRepository;
import com.sparta.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> follow(Long userId, FollowRequestDto requestDto,
                                 HttpServletRequest request) {

        if(request.getHeader("Refresh-Token")==null) {
            return ResponseDto.fail(ErrorCode.LOGIN_REQUIRED);
        }
        if(request.getHeader("Authorization") == null) {
            return ResponseDto.fail(ErrorCode.LOGIN_REQUIRED);
        }

        //로그인한 회원정보받아오기
        User user2 = validateUser(request);
        if (null == user2) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        //pathvariable로 누구를 팔로우할지
        User user1 = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("회원 정보가 없습니다.")
        );

        if (requestDto.getFollowState() == 0) {
            followRepository.save(Follow.builder()
                    .userTo(user1)
                    .userFrom(user2)
                    .build());

            return ResponseDto.success(FollowResponseDto.builder().isFollow(true).build());
        } else {
            followRepository.deleteByUserFromAndUserTo(user2,user1);
            return ResponseDto.success(FollowResponseDto.builder().isFollow(false).build());
        }

    }

    public User validateUser(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getUserFromAuthentication();
    }
}
