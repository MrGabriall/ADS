package ru.skypro.ads.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
