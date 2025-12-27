package com.simplifica.library.controllers;


import com.simplifica.library.dtos.user.UserRequestCreateDTO;
import com.simplifica.library.dtos.user.UserResponseDTO;
import com.simplifica.library.dtos.user.UserSignRequest;
import com.simplifica.library.entities.User;
import com.simplifica.library.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public  AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody  UserRequestCreateDTO req) {
        User user = userService.createUser(req.toEntity());

        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponseDTO.fromEntity(user));
    }


    @PostMapping("/sign")
    public  ResponseEntity<UserResponseDTO> signUser(@RequestBody UserSignRequest req) {
        User user = userService.sign(req.email(), req.password());

        return  ResponseEntity
                .status(HttpStatus.OK).body(UserResponseDTO.fromEntity(user));

    }





}
