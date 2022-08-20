package com.sparta.clone.controller;


import com.sparta.clone.controller.request.EditProfileRequestDto;
import com.sparta.clone.controller.request.LoginRequestDto;
import com.sparta.clone.controller.request.SignupRequestDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.domain.UserDetailsImpl;
import com.sparta.clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/api/users/signup")
    public ResponseDto<?> signup(@RequestBody SignupRequestDto requestDto) {

        return userService.signup(requestDto);
    }

    //로그인
    @PostMapping("/api/users/login")
    public ResponseDto<?> login(@RequestBody LoginRequestDto requestDto,
                                HttpServletResponse response) {

        return userService.login(requestDto, response);
    }

    //로그아웃
    @PostMapping("api/users/logout")
    public ResponseDto<?> logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    @Transactional
    @PutMapping ("api/users/edit/{userid}")
    public ResponseDto<?> editprofile(EditProfileRequestDto requestDto,@PathVariable long userid, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return userService.editprofile(requestDto,userid,userDetails);
    }

}
