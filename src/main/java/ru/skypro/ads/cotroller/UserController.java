package ru.skypro.ads.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.ads.entity.User;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class UserController {
    /*
    private UserService userService;
    private UserController(UserService userService) {
        this.userService = userService;
    }
     */

    @PostMapping(path ="users/set_password")
    public ResponseEntity<?> setPassword() {
        return null;
    }

    @GetMapping(path = "users/me/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        return null;
    }

    @PatchMapping(path = "users/me")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return null;
    }

    @PatchMapping(path = "users/me/image")
    public ResponseEntity<?> updateUserImage(@RequestBody String image) {
        return null;
    }

}
