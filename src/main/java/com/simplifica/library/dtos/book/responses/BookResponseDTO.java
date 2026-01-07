package com.simplifica.library.dtos.book.responses;
import com.simplifica.library.dtos.label.LabelSummaryDTO;
import com.simplifica.library.dtos.user.responses.UserSummaryDTO;
import com.simplifica.library.entities.Book;
import com.simplifica.library.entities.Label;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record BookResponseDTO(
        Long idBook,
        String title,
        String author,
        String edition,
        String volume,
        String publisher,
        String category,
        LocalDate publicationDate,
        String image,
        String status,
        String gender,
        UserSummaryDTO user,
        List<LabelSummaryDTO> labels
)
{
    public static   BookResponseDTO fromEntity(Book book) {
        return  new BookResponseDTO(
                book.getIdBook(),
                book.getTitle(),
                book.getAuthor(),
                book.getEdition(),
                book.getVolume(),
                book.getPublisher(),
                book.getCategory(),
                book.getPublicationDate(),
                book.getImage(),
                book.getStatus(),
                book.getGender(),
                book.getUser() == null ? null : UserSummaryDTO.fromEntity(book.getUser()),
                book.getLabels() == null ?
                        List.of() :
                        book.getLabels()
                                .stream()
                                .map(LabelSummaryDTO::fromEntity)
                                .toList()
        );
    }
}
