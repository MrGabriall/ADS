package ru.skypro.ads.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ads")
    private Ads ads;

    @Column(name = "file_path")
    private String filePath;
}
