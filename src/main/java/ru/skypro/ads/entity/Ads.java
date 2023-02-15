package ru.skypro.ads.entity;

import javax.persistence.*;

@Entity
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "image")
    private Image image;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private User authorId;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;
}
