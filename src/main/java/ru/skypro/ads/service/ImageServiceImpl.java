package ru.skypro.ads.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     *
     * @param ads
     * @param image
     * @param file
     * @return
     */
    public Image updateImage(Ads ads, Image image, MultipartFile file) {
        if (image != null) {
            checkImage(image);
            deleteImage(image);
        }
        return addImage(ads, file);
    }

    /**
     *
     * @param image
     * @return
     */
    public Pair<byte[], String> getImageData(Image image) {
        checkImage(image);
        return imageWriter.getImage(image.getFilePath());
    }

    /**
     *
     * @param image
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean deleteImage(Image image) {
        imageRepository.delete(image);
        boolean isDeleted = imageWriter.deleteImage(Path.of(image.getFilePath()));
        return isDeleted && imageRepository.findById(image.getId()).isEmpty();
    }

    /**
     *
     * @param ads
     * @param file
     * @return
     */
    public Image addImage(Ads ads, MultipartFile file) {
        Path path = imageWriter.writeImage(file, imagesDir);

        Image image = new Image();
        image.setFilePath(path.toString());
        image.setAdsId(ads.getId());
        image = imageRepository.save(image);
        return image;
    }

    /**
     *
     * @param image
     */
    private void checkImage(Image image) {
        if (image.getId() == null || imageRepository.findById(image.getId()).isEmpty()) {
            throw new ImageNotFoundException();
        }
    }
}
