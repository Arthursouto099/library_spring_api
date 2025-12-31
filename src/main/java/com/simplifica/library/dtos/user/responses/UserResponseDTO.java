package com.simplifica.library.dtos.user.responses;

import com.simplifica.library.dtos.book.responses.BookSummaryDTO;
import com.simplifica.library.entities.User;

import java.util.List;

public record UserResponseDTO(
        Long idUser,
        String name,
        String email,
        List<BookSummaryDTO> books

) {
    public static UserResponseDTO fromEntity(User user) {
        return  new UserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getBooks() == null ?
                        List.of() :
                        user.getBooks()
                                .stream()
                                .map(BookSummaryDTO::fromEntity)
                                .toList()

        );
    }

}
