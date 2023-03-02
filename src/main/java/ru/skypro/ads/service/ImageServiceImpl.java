package ru.skypro.ads.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.component.ImageWriter;
import ru.skypro.ads.entity.Ads;
import ru.skypro.ads.entity.Image;
import ru.skypro.ads.exception.ImageNotFoundException;
import ru.skypro.ads.repository.ImageRepository;

import java.nio.file.Path;

@Service
public class ImageServiceImpl {

    @Value("${application.images}")
    private String imagesDir;
    private final ImageRepository imageRepository;
    private final ImageWriter imageWriter;

    public ImageServiceImpl(ImageRepository imageRepository, ImageWriter imageWriter) {
        this.imageRepository = imageRepository;
        this.imageWriter = imageWriter;
    }

    public Image updateImage(Ads ads, Image image, MultipartFile file) {
        checkImage(image);

        if (deleteImage(image)) {
            return addImage(ads, file);
        } else return image;
    }

    public Pair<byte[], String> getImageData(Image image) {
        checkImage(image);
        return imageWriter.getImage(image.getFilePath());
    }

    public boolean deleteImage(Image image) {
        imageRepository.delete(image);
        boolean isDeleted = imageWriter.deleteImage(Path.of(image.getFilePath()));
        return isDeleted && imageRepository.findById(image.getId()).isEmpty();
    }

    public Image addImage(Ads ads, MultipartFile file) {
        Path path = imageWriter.writeImage(file, imagesDir);

        Image image = new Image();
        image.setFilePath(path.toString());
        image.setAds(ads);
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
