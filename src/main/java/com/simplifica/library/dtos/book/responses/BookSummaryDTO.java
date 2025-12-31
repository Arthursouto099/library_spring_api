package com.simplifica.library.dtos.book.responses;

import com.simplifica.library.entities.Book;

public record BookSummaryDTO(
        Long idBook,
        String title,
        String author
) {
    public static BookSummaryDTO fromEntity(Book book) {
        return new BookSummaryDTO(
                book.getIdBook(),
                book.getTitle(),
                book.getAuthor()
        );
    }
}
