package ru.skypro.ads.entity;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "authorId")
    private User authorId;

    @OneToOne
    @JoinColumn(name = "adsId")
    private Ads adsId;

    @Column(name = "text")
    private String text;

    @Column(name = "createdAt")
    private String createdAt;
}
