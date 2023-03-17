package ru.skypro.ads.service;


import ru.skypro.ads.dto.RegisterReq;
import ru.skypro.ads.dto.Role;

/**
 * Interface for authorisation
 */
public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
