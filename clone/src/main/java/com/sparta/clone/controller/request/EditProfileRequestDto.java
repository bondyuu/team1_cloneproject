package com.sparta.clone.controller.request;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileRequestDto {
    private String introduction;
    private MultipartFile imgFIle;




}
