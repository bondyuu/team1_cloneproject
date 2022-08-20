package com.sparta.clone.service;

import com.sparta.clone.controller.request.EditProfileRequestDto;
import com.sparta.clone.controller.request.LoginRequestDto;
import com.sparta.clone.controller.request.SignupRequestDto;
import com.sparta.clone.controller.request.TokenDto;
import com.sparta.clone.controller.response.LoginResponseDto;
import com.sparta.clone.controller.response.MessageResponseDto;
import com.sparta.clone.controller.response.ProfileResponseDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.domain.User;
import com.sparta.clone.domain.UserDetailsImpl;
import com.sparta.clone.global.error.ErrorCode;
import com.sparta.clone.jwt.TokenProvider;
import com.sparta.clone.repository.UserRepository;
import com.sparta.clone.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private final S3UploadService s3UploadService;


    @Transactional
    public ResponseDto<?> signup(SignupRequestDto requestDto) {

        if (null != isPresentUser(requestDto.getUsername())) {
            return ResponseDto.fail(ErrorCode.DUPLICATED_USERNAME);
        }

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            return ResponseDto.fail(ErrorCode.PASSWORDS_NOT_MATCHED);
        }

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();

        userRepository.save(user);

        return ResponseDto.success(MessageResponseDto.builder()
                        .msg("회원가입 성공")
                .build());
    }

    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        User user = isPresentUser(requestDto.getUsername());
        if (null == user) {
            return ResponseDto.fail(ErrorCode.USER_NOT_FOUND);
        }

        if (!user.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail(ErrorCode.INVALID_USER);
        }
        TokenDto tokenDto = tokenProvider.generateTokenDto(user);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                LoginResponseDto.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .token(tokenDto)
                        .build());
    }

    public ResponseDto<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }
        User user = tokenProvider.getUserFromAuthentication();
        if (null == user) {
            return ResponseDto.fail(ErrorCode.NOT_LOGIN_STATE);
        }

        return tokenProvider.deleteRefreshToken(user);
    }

    @Transactional(readOnly = true)
    public User isPresentUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    public ResponseDto<?> editprofile(EditProfileRequestDto requestDto,Long userid, UserDetailsImpl userDetails) throws IOException {

        User user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("찾을수없음"));


            String imgUrl = s3UploadService.upload(requestDto.getImgFIle(),"static");
            user.editProfile(ProfileResponseDto.builder()
                    .profileImage(imgUrl)
                    .introduction(requestDto.getIntroduction())
                    .build());

            return ResponseDto.success("수정 완료!");
    }
}
