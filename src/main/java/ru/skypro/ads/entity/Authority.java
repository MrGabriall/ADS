package ru.skypro.ads.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Entity for work with user authority session
 */
@Entity
@Table(name = "authorities")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "username")
    String username;

    @Column(name = "authority")
    String role;
}
