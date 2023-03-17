package ru.skypro.ads.component;

import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * @author mrgab
 *
 * This class work with multipart files in file system
 */
@Component
public class ImageWriter {

    /**
     * This method generate file path with random name for multipart file
     *
     * @param file
     * @param dir
     * @return Path for new file in file system
     */
    private Path generateFilePath(MultipartFile file, String dir) {
        File fileExist;
        String uuid;
        Path path = Paths.get(dir);
        String date = LocalDate.now().toString();
        String extension = Optional.ofNullable(file.getOriginalFilename())
                .map(fileName -> fileName.substring(file.getOriginalFilename().lastIndexOf('.')))
                .orElse("");
        do {
            uuid = String.valueOf(UUID.randomUUID());
            fileExist = path.resolve(file.getName() + "_" + uuid + date + extension).toFile();
        } while (fileExist.exists());
        return path.resolve(file.getName() + "_" + date + uuid + extension);
    }

    /**
     * This method write multipart file in file system
     *
     * @param file
     * @param dir
     * @return path in file system
     */
    public Path writeImage(MultipartFile file, String dir) {
        Path path = generateFilePath(file, dir);
        try {
            return Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error with write " + file + " in " + dir);
        }
    }

    /**
     * This method delete image in file system
     *
     * @param path
     * @return result of delete
     */
    public boolean deleteImage(Path path) {
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(path + " file in not removed in file system");
        }
    }

    /**
     * This method get pair for get image data
     *
     * @param path
     * @return byte[] and media type
     */
    public Pair<byte[], String> getImage(String path) {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Error with read bytes of " + path);
        }
        return Pair.of(bytes, MediaType.IMAGE_JPEG_VALUE);
    }


}
