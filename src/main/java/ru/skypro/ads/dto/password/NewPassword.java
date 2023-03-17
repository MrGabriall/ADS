package ru.skypro.ads.dto.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for change password requests
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPassword {
    private String currentPassword;
    private String newPassword;
}
