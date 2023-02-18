package ru.skypro.ads.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@RequiredArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "author_id")
    private User authorId;

    @OneToOne
    @JoinColumn(name = "ads_id")
    private Ads adsId;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private String createdAt;
}
