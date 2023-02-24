package ru.skypro.ads.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.component.RecordMapper;
import ru.skypro.ads.dto.Role;
import ru.skypro.ads.dto.UserRecord;
import ru.skypro.ads.dto.password.NewPassword;
import ru.skypro.ads.entity.Avatar;
import ru.skypro.ads.entity.User;
import ru.skypro.ads.repository.AvatarRepository;
import ru.skypro.ads.repository.UserRepository;

import java.time.LocalDate;

@Service
public class UserService {
    private final UserRepository userRepository;
    private User user;
    private final RecordMapper recordMapper;
    private final AvatarService avatarService;
    @Value("${application.basic-avatar}")
    private String basicAvatarPath;
    private final AvatarRepository avatarRepository;

    public UserService(UserRepository userRepository, RecordMapper recordMapper, AvatarService avatarService,
                       AvatarRepository avatarRepository) {
        this.userRepository = userRepository;
        this.recordMapper = recordMapper;
        this.avatarService = avatarService;
        this.avatarRepository = avatarRepository;
    }

    private User getSingleUser(){
        if (user == null){
            user = new User();
            user.setEmail("user@gmail.com");
            user.setPassword("password");
            user.setUserName("User");
            user.setPhone("+79881234567");
            user.setFirstName("Работяга");
            user.setLastName("Дефолтный");
            user.setRole(Role.USER);
            user.setCity("Сочи");
            user.setRegDate(LocalDate.now().toString());
            Avatar avatar = new Avatar();
            avatar.setFilePath(basicAvatarPath);
            avatarRepository.save(avatar);
            user.setAvatar(avatar);
            userRepository.save(user);
            return user;
        } else {
            return user;
        }
    }

    public NewPassword setPassword(NewPassword newPassword){
        User singleUser = getSingleUser();
        if (singleUser.getPassword().equals(newPassword.getCurrentPassword())){
            singleUser.setPassword(newPassword.getNewPassword());
            userRepository.save(singleUser);
        }
        return newPassword;
    }

    public UserRecord getUser() {
        return recordMapper.toRecord(getSingleUser());
    }

    public void updateUser(UserRecord userRecord){
        User userEntity = recordMapper.toEntity(userRecord);
        User currentUser = getSingleUser();
        currentUser.setEmail(userEntity.getEmail());
        currentUser.setFirstName(userEntity.getFirstName());
        currentUser.setLastName(userEntity.getLastName());
        currentUser.setPhone(userEntity.getPhone());
        currentUser.setCity(userEntity.getCity());
        userRepository.save(currentUser);
    }

    public void updateUserImage(MultipartFile image){
        User currentUser = getSingleUser();
        avatarService.updateAvatar(currentUser.getAvatar(), image);
    }
}
