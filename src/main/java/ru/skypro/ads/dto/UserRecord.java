package ru.skypro.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRecord {
    private Integer id;
    private String phone;
    private String lastName;
    private String firstName;
    private String email;
    private String city;
    private String regDate;
    private String image;
}
