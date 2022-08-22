package com.sparta.clone.service;

import com.amazonaws.Response;
import com.sparta.clone.controller.request.CommentRequestDto;
import com.sparta.clone.controller.response.MessageResponseDto;
import com.sparta.clone.controller.response.ResponseDto;
import com.sparta.clone.domain.Comment;
import com.sparta.clone.domain.Post;
import com.sparta.clone.domain.User;
import com.sparta.clone.global.error.ErrorCode;
import com.sparta.clone.jwt.TokenProvider;
import com.sparta.clone.repository.CommentRepository;
import com.sparta.clone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> create(Long postId, CommentRequestDto requestDto,
                                        HttpServletRequest request) {
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

        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail(ErrorCode.POST_NOT_FOUND);
        }

        Comment comment = commentRepository.save(Comment.builder()
                .user(user)
                .post(post)
                .comment(requestDto.getComment())
                .build()
        );

        return ResponseDto.success(MessageResponseDto.builder().msg("댓글 작성 성공").build());
    }

    public ResponseDto<?> delete(Long commentId, HttpServletRequest request) {
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

        Comment comment = isPresentComment(commentId);
        commentRepository.delete(comment);

        return ResponseDto.success(MessageResponseDto.builder().msg("댓글 삭제 성공").build());
    }


    public User validateUser(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getUserFromAuthentication();
    }

    public Post isPresentPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.orElse(null);
    }

    public Comment isPresentComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElse(null);
    }
}
