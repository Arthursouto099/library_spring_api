package com.simplifica.library.dtos.user.requests;


import com.simplifica.library.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRequestUpdateDTO(
        @Size(min = 2, max = 80, message = "Nome deve ter entre 2 e 80 caracteres")
        String name,

        @Email(message = "Email precisa ser válido")
        String email
) {}
