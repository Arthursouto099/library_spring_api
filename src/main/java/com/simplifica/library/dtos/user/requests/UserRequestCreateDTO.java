package com.simplifica.library.dtos.user.requests;

import com.simplifica.library.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestCreateDTO(
        @NotEmpty(message = "Nome é obrigátorio")
        String name,
        @NotEmpty(message = "Email é obrigátorio")
        @Email(message = "Email precisa ser válido")
        String email,
        @NotEmpty(message = "Password é obrigátorio")
        String password
) {


    public  User toEntity() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return  user;
    };
}
