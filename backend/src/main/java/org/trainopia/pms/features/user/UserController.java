package org.trainopia.pms.features.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trainopia.pms.features.user.dto.UpsertUserDTO;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping
    public ResponseEntity<?> findAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public User createUser(@RequestBody UpsertUserDTO upsertUserDTO) {
        return userService.create(upsertUserDTO);
    }
}
