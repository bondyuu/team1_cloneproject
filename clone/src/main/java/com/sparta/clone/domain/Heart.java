package com.sparta.clone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.clone.controller.request.CreateHeartRequestDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Heart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonIgnore
    private Long id;

    @Column
    private Long likeuserid;


    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;



    public Heart(CreateHeartRequestDto createHeartRequestDto) {
        this.likeuserid = createHeartRequestDto.getUserid();
        this.post = createHeartRequestDto.getPost();
    }

    public void confirmPost(Post post) {
        this.post = post;
        post.addHeartList(this);
    }




}
