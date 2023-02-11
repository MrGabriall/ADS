package ru.skypro.ads.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.entity.Image;
import ru.skypro.ads.entity.User;
import ru.skypro.ads.service.UserService;

import java.io.IOException;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;
    private UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(path ="/set_password")
    public ResponseEntity<?> setPassword(@PathVariable String currentPassword, @PathVariable String newPassword) {
        return null;
    }

    @GetMapping(path = "/me/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        return null;
    }

    @PatchMapping(path = "/me")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return null;
    }

    @PostMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadUserImage(@PathVariable Long id,
                                               @RequestParam MultipartFile image) throws IOException {
        //userService.uploadUserImage(id, image);
        return ResponseEntity.ok().build();
    }

}
