package ru.skypro.ads.entity;

import lombok.*;
import ru.skypro.ads.dto.Role;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "reg_date")
    private String regDate;

    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
