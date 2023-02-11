package ru.skypro.ads.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class UserRecord {
    private Long id;
    private String phone;
    private String lastName;
    private String firstName;
    private String email;
    private String city;
    private String regDate;
}
