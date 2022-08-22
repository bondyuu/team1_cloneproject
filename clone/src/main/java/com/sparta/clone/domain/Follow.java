package com.sparta.clone.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "userTo")
    private User userTo;

    @ManyToOne
    @JoinColumn(name = "userFrom")
    private User userFrom;
}
