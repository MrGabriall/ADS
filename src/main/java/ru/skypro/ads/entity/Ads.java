package ru.skypro.ads.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToMany
    @JoinColumn(name = "images")
    private List<Image> images;

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
