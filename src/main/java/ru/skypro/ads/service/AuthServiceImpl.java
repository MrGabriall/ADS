package ru.skypro.ads.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.ads.dto.RegisterReq;
import ru.skypro.ads.dto.Role;
import ru.skypro.ads.entity.User;
import ru.skypro.ads.repository.UserRepository;


@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserRepository userRepository;


    public AuthServiceImpl(UserService userService, PasswordEncoder encoder,
                           UserRepository userRepository) {
        this.encoder = encoder;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(String username, String password) {
        if (!userService.userExists(username)) {
            log.warn("Error: " + username + " doesn't exist.");
            return false;
        }
        User user = userRepository.findByUsername(username);
        String encryptedPassword = user.getPassword();

        if (!encoder.matches(password, encryptedPassword)) {
            log.warn("Error: " + username + " Entered wrong password.");
            return false;
        }
        return true;
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (userRepository.existsUserByUsername(registerReq.getUsername())) {
            log.warn("Error: " + registerReq.getUsername() + " - is already exist.");
            return false;
        }
        registerReq.setPassword(encoder.encode(registerReq.getPassword()));
        userService.createUser(registerReq);
        return true;
    }
}
