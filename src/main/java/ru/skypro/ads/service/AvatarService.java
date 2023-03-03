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

    public void updateAvatar(Avatar avatar, MultipartFile file) {
        if (avatar != null) {
            checkImage(avatar);
            deleteAvatarInFS(avatar);
            addAvatar(avatar, file);
        }
    }

    public Pair<byte[], String> getAvatarData(Avatar avatar) {
        checkImage(avatar);
        return imageWriter.getImage(avatar.getFilePath());
    }

    private void deleteAvatarInFS(Avatar avatar) {
        imageWriter.deleteImage(Path.of(avatar.getFilePath()));
    }

    public void addAvatar(Avatar avatar, MultipartFile file) {
        Path path = imageWriter.writeImage(file, avatarsDir);

        avatar.setFilePath(path.toString());
        avatarRepository.save(avatar);
    }

    private void checkImage(Avatar avatar) {
        if (avatar.getId() == null || avatarRepository.findById(avatar.getId()).isEmpty()) {
            throw new AvatarNotFoundException();
        }
    }
}
