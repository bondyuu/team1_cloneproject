package com.sparta.clone.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.util.LinkedList;
import java.util.List;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    @Column(nullable = false)
    private String content;

    @Column
    private String imageUrl;

    @Column
    private int likeCnt;

    @Column
    @OneToMany(mappedBy = "post",cascade = ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Heart> heartList = new LinkedList<>();

    public void addHeartList(Heart heart) {
        heartList.add(heart);
    }
    public void like () {
        this.likeCnt += 1;
    }
    public void dislike() {
        this.likeCnt -= 1;
    }


}
