package ru.skypro.ads.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Entity for work with ads image
 */
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

    @Column(name = "ads_id")
    private Integer adsId;

    @Column(name = "file_path")
    private String filePath;
}
