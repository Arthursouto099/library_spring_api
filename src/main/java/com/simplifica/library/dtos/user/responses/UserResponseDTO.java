package com.simplifica.library.dtos.user.responses;

import com.simplifica.library.dtos.book.responses.BookSummaryDTO;
import com.simplifica.library.dtos.label.LabelSummaryDTO;
import com.simplifica.library.entities.User;

import java.util.List;

public record UserResponseDTO(
        Long idUser,
        String name,
        String email,
        List<BookSummaryDTO> books,
        List<LabelSummaryDTO> labels

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
                                .toList(),
                user.getLabels() == null ?
                        List.of() :
                        user.getLabels()
                                .stream()
                                .map(LabelSummaryDTO::fromEntity)
                                .toList()
        );
    }

}
