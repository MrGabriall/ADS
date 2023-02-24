package ru.skypro.ads.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "avatars")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "file_path")
    private String filePath;
}
