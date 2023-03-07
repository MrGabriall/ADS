package ru.skypro.ads.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.component.RecordMapper;
import ru.skypro.ads.dto.Role;
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

    public UserService(UserRepository userRepository, RecordMapper recordMapper, AvatarService avatarService,
                       AvatarRepository avatarRepository) {
        this.userRepository = userRepository;
        this.recordMapper = recordMapper;
        this.avatarService = avatarService;
        this.avatarRepository = avatarRepository;
    }
/*
    private User getSingleUser() {
        if (user == null) {
            user = new User();
            user.setId(1);
            user.setFirstName("Работяга");
            user.setLastName("Дефолтный");
            user.setEmail("user@gmail.com");
            user.setPhone("+79881234567");
            user.setCity("Сочи");
            user.setRegDate(LocalDate.now().toString());
            user.setUserName("User");
            user.setPassword("password");
            user.setRole(Role.ADMIN);
            user = userRepository.save(user);
        }
        return user;
    }
 */
//todo email added
    public UserRecord getUser(String email) {
        return recordMapper.toRecord(userRepository.findByEmail(email));
    }

//todo должен быть второй параметр  - email!
    public NewPassword setPassword(NewPassword newPassword, String email) {
        User singleUser = userRepository.findByEmail(email);
        if (singleUser.getPassword().equals(newPassword.getCurrentPassword())) {
            singleUser.setPassword(newPassword.getNewPassword());
            userRepository.save(singleUser);
        }
        return newPassword;
    }
//todo должен быть второй параметр  - email! Везде где имеется getSingleUser применить userRepository.findByEmail()
    public UserRecord updateUser(UserRecord userRecord, String email) {
        User userEntity = recordMapper.toEntity(userRecord);
        User currentUser = userRepository.findByEmail(email);
        currentUser.setFirstName(userEntity.getFirstName());
        currentUser.setLastName(userEntity.getLastName());
        currentUser.setPhone(userEntity.getPhone());
        user = userRepository.save(currentUser);
        return recordMapper.toRecord(user);
    }

    public void updateUserImage(MultipartFile image, String email) {
        User currentUser = userRepository.findByEmail(email);
        currentUser.setAvatar(avatarService.updateAvatar(currentUser.getAvatar(), image));
        user = userRepository.save(currentUser);
    }

    public Pair<byte[], String> getAvatarById(Integer avatarId) {
        Avatar avatar = avatarRepository.findById(avatarId).orElseThrow(AvatarNotFoundException::new);
        return avatarService.getAvatarData(avatar);
    }
}
