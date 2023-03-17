package ru.skypro.ads.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Entity for work with comment
 */
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
    @JoinColumn(name = "author")
    private User author;

    @OneToOne
    @JoinColumn(name = "ads")
    private Ads ads;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private String createdAt;
}
