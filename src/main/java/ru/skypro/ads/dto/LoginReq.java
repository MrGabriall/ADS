package ru.skypro.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for work login request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {
    private String password;
    private String username;
}
