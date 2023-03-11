package ru.skypro.ads.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.ads.dto.RegisterReq;
import ru.skypro.ads.dto.Role;



@Service
public class AuthServiceImpl implements AuthService {

    private static Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    private final UserService userService;


    public AuthServiceImpl(UserDetailsManager manager, UserService userService, PasswordEncoder encoder) {
        this.manager = manager;
        this.encoder = encoder;
        this.userService = userService;
    }

    @Override
    public boolean login(String username, String password) {
        log.info("Starting logging: " + username);
        if (!manager.userExists(username)) {
            log.warn("Error. Username: " + username + "doesn't exist.");
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(username);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        if (!encoder.matches(password, encryptedPassword)) {
            log.warn("Error. Entered wrong password");
            return false;
        }
        log.info("Logging: " + username + " - completed successfully.");
        return true;
    }
//todo добавить исключения
    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        log.info("Starting process registration: " + registerReq.getUsername());
        if (manager.userExists(registerReq.getUsername())) {
            log.warn("Error. This username: " +  registerReq.getUsername() + " - is already exist.");
            return false;
        }
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .password(registerReq.getPassword().substring(8))
                        .username(registerReq.getUsername())
                        .roles(Role.USER.name())
                        .build()
        );
        userService.createUser(registerReq);
        log.info("Registration " + registerReq.getUsername() + " is successfully complete");
        return true;
    }
}
