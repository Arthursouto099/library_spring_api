package com.simplifica.library.dtos.user.responses;

import com.simplifica.library.entities.Book;
import com.simplifica.library.entities.User;

import java.util.List;

public record UserResponseDTO(
        Long idUser,
        String name,
        String email

) {
    public static UserResponseDTO fromEntity(User user) {
        return  new UserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail()
        );
    }

}
