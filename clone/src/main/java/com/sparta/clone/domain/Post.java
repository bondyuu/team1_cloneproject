package com.sparta.clone.domain;


import com.sparta.clone.controller.request.PostRequestDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @Column(nullable = false)
    private String content;

    @Column
    private String imageUrl;

    @Column
    private boolean likestate;

    @Column
    private int likeCnt;

    @Column
    @OneToMany(mappedBy = "post",cascade = ALL, orphanRemoval = true)
    private List<Comment> commentList;


    public void like () {
        this.likeCnt += 1;
    }
    public void dislike() {
        this.likeCnt -= 1;
    }


}
