package ru.skypro.ads.component;

import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class ImageWriter {

    private Path generateFilePath(MultipartFile file, String dir) {
        String date = LocalDate.now().toString();
        String extension = Optional.ofNullable(file.getOriginalFilename())
                .map(fileName -> fileName.substring(file.getOriginalFilename().lastIndexOf('.')))
                .orElse("");
        return Paths.get(dir).resolve(file.getName() + "_" + date + extension);
    }

    public Path writeImage(MultipartFile file, String dir) {
        Path path = generateFilePath(file, dir);
        try {
            return Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error with write " + file + " in " + dir);
        }
    }

    public boolean deleteImage(Path path) {
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(path + " file in not removed in file system");
        }
    }

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
