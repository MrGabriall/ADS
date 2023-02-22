package ru.skypro.ads.service;

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

    @Value("${application.avatars}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final ImageWriter imageWriter;

    public AvatarService(AvatarRepository avatarRepository, ImageWriter imageWriter) {
        this.avatarRepository = avatarRepository;
        this.imageWriter = imageWriter;
    }

    public Avatar updateAvatar(Avatar avatar, MultipartFile file) {
        checkImage(avatar);

        if (deleteAvatar(avatar)) {
            return addAvatar(file);
        } else return avatar;
    }

    public Pair<byte[], String> getAvatarData(Avatar avatar) {
        checkImage(avatar);
        return imageWriter.getImage(avatar.getFile());
    }

    private boolean deleteAvatar(Avatar avatar) {
        avatarRepository.delete(avatar);
        boolean isDeleted = imageWriter.deleteImage(Path.of(avatar.getFile()));

        if (isDeleted && avatarRepository.findById(avatar.getId()).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Avatar addAvatar(MultipartFile file) {
        Path path = imageWriter.writeImage(file, avatarsDir);

        Avatar avatar = new Avatar();
        avatar.setFile(path.toString());
        avatar = avatarRepository.save(avatar);
        return avatar;
    }

    private void checkImage(Avatar avatar) {
        if (avatar == null || avatar.getId() == null ||
                avatarRepository.findById(avatar.getId()).isEmpty()) {
            throw new AvatarNotFoundException();
        }
    }
}
