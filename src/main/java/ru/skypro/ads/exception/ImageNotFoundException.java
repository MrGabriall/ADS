package ru.skypro.ads.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(Integer id) {
        super("Image" + id + " not found");
    }

    public ImageNotFoundException() {
    }
}
