package com.simplifica.library.dtos.user.responses;

import com.simplifica.library.entities.User;

public record UserSummaryDTO(
        Long idUser,
        String name,
        String email
) {
    public static UserSummaryDTO fromEntity(User user) {
        return new UserSummaryDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail()
        );
    }
}
