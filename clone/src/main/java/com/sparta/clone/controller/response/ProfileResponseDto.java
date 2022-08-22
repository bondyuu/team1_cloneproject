package com.sparta.clone.controller.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

    private String profileImage;
    private String introduction;

}
