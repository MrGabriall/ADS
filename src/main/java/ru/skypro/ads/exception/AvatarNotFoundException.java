package ru.skypro.ads.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AvatarNotFoundException extends RuntimeException {
    public AvatarNotFoundException(String message) {
        super(message);
    }

    public AvatarNotFoundException(Integer id) {
        super("Avatar" + id + " not found");
    }

    public AvatarNotFoundException() {
        super("Avatar not found");
    }
}
