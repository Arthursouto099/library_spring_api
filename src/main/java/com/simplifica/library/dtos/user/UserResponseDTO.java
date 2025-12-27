package com.simplifica.library.dtos.user;

import com.simplifica.library.entities.User;

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
