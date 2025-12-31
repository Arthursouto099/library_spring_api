package com.simplifica.library.dtos.book.responses;
import com.simplifica.library.dtos.user.responses.UserSummaryDTO;
import com.simplifica.library.entities.Book;


import java.time.LocalDate;

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
        UserSummaryDTO user
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
                book.getUser() == null ? null : UserSummaryDTO.fromEntity(book.getUser())
        );
    }
}
