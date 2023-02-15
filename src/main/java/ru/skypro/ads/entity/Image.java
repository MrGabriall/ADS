package ru.skypro.ads.entity;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ads_id")
    private Ads adsId;

    @Column(name = "file")
    private String file;
}
