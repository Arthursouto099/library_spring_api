package com.simplifica.library.dtos.book.requests;

import com.simplifica.library.entities.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BookCreateRequestDTO(
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
        String title,

        @NotBlank(message = "Autor é obrigatório")
        @Size(max = 120, message = "Autor deve ter no máximo 120 caracteres")
        String author,

        @Size(max = 50, message = "Edição deve ter no máximo 50 caracteres")
        String edition,

        @Size(max = 30, message = "Volume deve ter no máximo 30 caracteres")
        String volume,

        @NotBlank(message = "Editora é obrigatória")
        @Size(max = 100, message = "Editora deve ter no máximo 100 caracteres")
        String publisher,

        @Size(max = 50, message = "Categoria deve ter no máximo 50 caracteres")
        String category,

        @PastOrPresent(message = "A data de publicação não pode ser futura")
        LocalDate publicationDate,

        @Size(max = 255, message = "URL da imagem muito longa")
        String image
) {
    public Book toEntity() {
        Book book = new Book();

        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setEdition(this.edition);
        book.setVolume(this.volume);
        book.setPublisher(this.publisher);
        book.setCategory(this.category);
        book.setPublicationDate(this.publicationDate);
        book.setImage(this.image);

        return book;
    }

    public void updateEntity(Book book) {
        if (this.title != null) book.setTitle(this.title);
        if (this.author != null) book.setAuthor(this.author);
        if (this.edition != null) book.setEdition(this.edition);
        if (this.volume != null) book.setVolume(this.volume);
        if (this.publisher != null) book.setPublisher(this.publisher);
        if (this.category != null) book.setCategory(this.category);
        if (this.publicationDate != null) book.setPublicationDate(this.publicationDate);
        if (this.image != null) book.setImage(this.image);
    }
}
