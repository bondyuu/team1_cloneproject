package com.sparta.clone.controller;


import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.domain.UserDetailsImpl;
import com.sparta.clone.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserPageController {

    private final UserPageService userPageService;

    @GetMapping("api/detail/{username}")
    public ResponseDto<?> getUserPage(@PathVariable String username, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userPageService.getUserPage(username, userDetails);
    }
}
