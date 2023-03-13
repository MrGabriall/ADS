package ru.skypro.ads.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.component.RecordMapper;
import ru.skypro.ads.dto.RegisterReq;
import ru.skypro.ads.dto.Role;
import ru.skypro.ads.dto.UserRecord;
import ru.skypro.ads.dto.password.NewPassword;
import ru.skypro.ads.entity.Authority;
import ru.skypro.ads.entity.Avatar;
import ru.skypro.ads.entity.User;
import ru.skypro.ads.exception.AvatarNotFoundException;
import ru.skypro.ads.repository.AuthorityRepository;
import ru.skypro.ads.repository.AvatarRepository;
import ru.skypro.ads.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static User user;
    private final RecordMapper recordMapper;
    private final AvatarService avatarService;
    private final AvatarRepository avatarRepository;
    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, RecordMapper recordMapper, AvatarService avatarService,
                       AvatarRepository avatarRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.recordMapper = recordMapper;
        this.avatarService = avatarService;
        this.avatarRepository = avatarRepository;
        this.authorityRepository = authorityRepository;
    }

    public UserRecord getUser(String username) {
        return recordMapper.toRecord(userRepository.findByUsername(username));
    }

    public NewPassword setPassword(NewPassword newPassword, String username) {
        User user = userRepository.findByUsername(username);
        if (user.getPassword().equals(newPassword.getCurrentPassword())) {
            user.setPassword(newPassword.getNewPassword());
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

    public void createUser(RegisterReq registerReq) {
        User newUser = recordMapper.toEntity(registerReq);
        Authority auth = new Authority();
        auth.setUsername(registerReq.getUsername());
        auth.setRole(Role.ROLE_USER.toString());
        authorityRepository.save(auth);
        userRepository.save(newUser);
    }

    public boolean userExists(String username) {
        return userRepository.existsUserByUsername(username);
    }
}
