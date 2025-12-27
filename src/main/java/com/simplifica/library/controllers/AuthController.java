package com.simplifica.library.controllers;
import com.simplifica.library.config.authentication.TokenConfig;
import com.simplifica.library.dtos.user.requests.UserRequestCreateDTO;
import com.simplifica.library.dtos.user.responses.UserResponseDTO;
import com.simplifica.library.dtos.user.requests.UserSignRequest;
import com.simplifica.library.dtos.user.responses.UserSignResponse;
import com.simplifica.library.entities.User;
import com.simplifica.library.exceptions.UnauthorizedResourceException;
import com.simplifica.library.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private  final TokenConfig tokenConfig;

    public  AuthController(UserService userService, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @GetMapping("/authenticated")
    public  ResponseEntity<String> authenticated() {
        return  ResponseEntity.status(HttpStatus.OK).body("Authenticado");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody  UserRequestCreateDTO req) {
        User user = userService.createUser(req.toEntity());

        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponseDTO.fromEntity(user));
    }


    @PostMapping("/sign")
    public  ResponseEntity<UserSignResponse> signUser(@Valid @RequestBody UserSignRequest req) {
        // Authenticação com email e senha.
        try {
            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(req.email(), req.password());
            Authentication authentication = authenticationManager.authenticate(userAndPass);

            User user = (User) authentication.getPrincipal();
            String token = tokenConfig.generateToken(user);

            return  ResponseEntity
                    .status(HttpStatus.OK).body(new UserSignResponse(token));
        }
        catch (AuthenticationException ex) {
            throw  new UnauthorizedResourceException("Credenciais Inválidas");
        }

    }
}
