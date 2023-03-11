package ru.skypro.ads.service;

import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.component.RecordMapper;
import ru.skypro.ads.dto.RegisterReq;
import ru.skypro.ads.dto.UserRecord;
import ru.skypro.ads.dto.password.NewPassword;
import ru.skypro.ads.entity.Avatar;
import ru.skypro.ads.entity.User;
import ru.skypro.ads.exception.AvatarNotFoundException;
import ru.skypro.ads.repository.AvatarRepository;
import ru.skypro.ads.repository.UserRepository;

import java.time.LocalDate;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static User user;
    private final RecordMapper recordMapper;
    private final AvatarService avatarService;
    private final AvatarRepository avatarRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RecordMapper recordMapper, AvatarService avatarService,
                       AvatarRepository avatarRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.recordMapper = recordMapper;
        this.avatarService = avatarService;
        this.avatarRepository = avatarRepository;
        this.passwordEncoder = passwordEncoder;
    }
//todo username added
    public UserRecord getUser(String username) {
        return recordMapper.toRecord(userRepository.findByUsername(username));
    }

//todo должен быть второй параметр  - username!
    public NewPassword setPassword(NewPassword newPassword, String username) {
        User user = userRepository.findByUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            String encodePassword = bCryptPasswordEncoder.encode(newPassword.getNewPassword());
            user.setPassword(encodePassword.substring(8));
            userRepository.save(user);
        }
        return newPassword;
    }

    public UserRecord updateUser(UserRecord userRecord, String username) {
        User userEntity = recordMapper.toEntity(userRecord);
        User currentUser = userRepository.findByUsername(username);
        currentUser.setFirstName(userEntity.getFirstName());
        currentUser.setLastName(userEntity.getLastName());
        currentUser.setPhone(userEntity.getPhone());
        user = userRepository.save(currentUser);
        return recordMapper.toRecord(user);
    }

    public void updateUserImage(MultipartFile image, String username) {
        User currentUser = userRepository.findByUsername(username);
        currentUser.setAvatar(avatarService.updateAvatar(currentUser.getAvatar(), image));
        user = userRepository.save(currentUser);
    }

    public Pair<byte[], String> getAvatarById(Integer avatarId) {
        Avatar avatar = avatarRepository.findById(avatarId).orElseThrow(AvatarNotFoundException::new);
        return avatarService.getAvatarData(avatar);
    }
    public void updateUser(RegisterReq registerReq) {
        User currentUser = userRepository.findByUsername(registerReq.getUsername());
        User fullUser = new User();
        fullUser.setId(currentUser.getId());
        fullUser.setUsername(currentUser.getUsername());
        fullUser.setPassword(currentUser.getPassword());
        fullUser.setFirstName(registerReq.getFirstName());
        fullUser.setLastName(registerReq.getLastName());
        fullUser.setPhone(registerReq.getPhone());
        fullUser.setRole(registerReq.getRole());
        fullUser.setRegDate(LocalDate.now().toString());
        userRepository.save(fullUser);
    }
}
