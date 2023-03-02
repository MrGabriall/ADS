package ru.skypro.ads.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.component.ImageWriter;
import ru.skypro.ads.entity.Avatar;
import ru.skypro.ads.exception.AvatarNotFoundException;
import ru.skypro.ads.repository.AvatarRepository;

import java.nio.file.Path;

@Service
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(AdsService.class);
    @Value("${application.avatars}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final ImageWriter imageWriter;

    public AvatarService(AvatarRepository avatarRepository, ImageWriter imageWriter) {
        this.avatarRepository = avatarRepository;
        this.imageWriter = imageWriter;
    }

    public void updateAvatar(Avatar avatar, MultipartFile file) {
        if (avatar != null) {
            checkImage(avatar);
            deleteAvatar(avatar);
        }
        addAvatar(file);
    }

    public Pair<byte[], String> getAvatarData(Avatar avatar) {
        checkImage(avatar);
        return imageWriter.getImage(avatar.getFilePath());
    }

    private void deleteAvatar(Avatar avatar) {
        avatarRepository.delete(avatar);
        imageWriter.deleteImage(Path.of(avatar.getFilePath()));

        boolean isAvatarDeleted = avatarRepository.findById(avatar.getId()).isEmpty();
        if (!isAvatarDeleted) {
            //logger.error(avatar + " isn't removed in DB");
        }
    }

    public void addAvatar(MultipartFile file) {
        Path path = imageWriter.writeImage(file, avatarsDir);

        Avatar avatar = new Avatar();
        avatar.setFilePath(path.toString());
        avatarRepository.save(avatar);
    }

    private void checkImage(Avatar avatar) {
        if (avatar.getId() == null || avatarRepository.findById(avatar.getId()).isEmpty()) {
            throw new AvatarNotFoundException();
        }
    }
}
