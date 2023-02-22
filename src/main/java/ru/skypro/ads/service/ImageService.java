package ru.skypro.ads.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.component.ImageWriter;
import ru.skypro.ads.entity.Image;
import ru.skypro.ads.exception.ImageNotFoundException;
import ru.skypro.ads.repository.ImageRepository;

import java.nio.file.Path;

@Service
public class ImageService {

    @Value("${application.avatars}")
    private String imagesDir;
    private ImageRepository imageRepository;
    private ImageWriter imageWriter;

    public ImageService(ImageRepository imageRepository, ImageWriter imageWriter) {
        this.imageRepository = imageRepository;
        this.imageWriter = imageWriter;
    }

    public Image updateImage(Image image, MultipartFile file) {
        checkImage(image);

        if (deleteImage(image)) {
            return addImage(file);
        } else return image;
    }

    public Pair<byte[], String> getImageData(Image image) {
        checkImage(image);
        return imageWriter.getImage(image.getFile());
    }

    public boolean deleteImage(Image image) {
        imageRepository.delete(image);
        boolean isDeleted = imageWriter.deleteImage(Path.of(image.getFile()));

        if (isDeleted && imageRepository.findById(image.getId()).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Image addImage(MultipartFile file) {
        Path path = imageWriter.writeImage(file, imagesDir);

        Image image = new Image();
        image.setFile(path.toString());
        image = imageRepository.save(image);
        return image;
    }

    private void checkImage(Image image) {
        if (image == null || image.getId() == null ||
                imageRepository.findById(image.getId()).isEmpty()) {
            throw new ImageNotFoundException();
        }
    }
}
