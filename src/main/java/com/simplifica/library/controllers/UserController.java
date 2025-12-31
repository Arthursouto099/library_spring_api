package com.simplifica.library.controllers;


import com.simplifica.library.config.authentication.CokieConfig;
import com.simplifica.library.config.authentication.TokenConfig;
import com.simplifica.library.dtos.user.requests.UserRequestUpdateDTO;
import com.simplifica.library.dtos.user.responses.UserResponseDTO;
import com.simplifica.library.entities.User;
import com.simplifica.library.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/user/config")
public class UserController {
    private final UserService userService;
    private  final  TokenConfig tokenConfig;
    private  final CokieConfig cokieConfig;

    public UserController(UserService userService, TokenConfig tokenConfig, CokieConfig cokieConfig) {
        this.userService = userService;
        this.tokenConfig = tokenConfig;
        this.cokieConfig = cokieConfig;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(@AuthenticationPrincipal User principal) {
        User user = userService.findByEmail(principal.getUsername());
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }


    @PutMapping("/me")
    public  ResponseEntity<UserResponseDTO> edit(@AuthenticationPrincipal UserDetails principal, @RequestBody UserRequestUpdateDTO req,
    HttpServletResponse res
    ) {
        User updated = userService.editUser(principal.getUsername(), req);

        if(!principal.getUsername().equalsIgnoreCase(updated.getEmail())) {
            String newToken = tokenConfig.generateToken(updated);

            ResponseCookie cokie = cokieConfig.generateCokie(newToken);

            res.addHeader(HttpHeaders.SET_COOKIE, cokie.toString());
        }

        return  ResponseEntity.ok(UserResponseDTO.fromEntity(updated));
    }

    @DeleteMapping("/me")
    public  ResponseEntity<?> delete(@AuthenticationPrincipal UserDetails principal) {
        userService.deleteUser(principal.getUsername());
        return ResponseEntity.noContent().build();
    }



}
