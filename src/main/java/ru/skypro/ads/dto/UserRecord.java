package ru.skypro.ads.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for work with users
 */
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
    @JsonProperty("image")
    private String avatarPath;
}
