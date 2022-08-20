package com.sparta.clone.controller;


import com.sparta.clone.controller.request.IdCheckDto;
import com.sparta.clone.controller.request.LoginRequestDto;
import com.sparta.clone.controller.request.SearchRequestDto;
import com.sparta.clone.controller.request.SignupRequestDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


    //아이디 중복 확인
    @PostMapping("api/users")
    public ResponseDto<?> check(@RequestBody IdCheckDto idCheckDto) {
        return userService.check(idCheckDto);
    }

    @PostMapping("/api/users/search")
    public ResponseDto<?> search(@RequestBody SearchRequestDto requestDto) {
        return userService.search(requestDto);
    }
}
