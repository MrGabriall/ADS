package ru.skypro.ads.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
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
