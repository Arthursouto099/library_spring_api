package com.simplifica.library.dtos.book.requests;

import com.simplifica.library.entities.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BookUpdateRequestDTO(
        @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
        String title,

        @Size(max = 120, message = "Autor deve ter no máximo 120 caracteres")
        String author,

        @Size(max = 50, message = "Edição deve ter no máximo 50 caracteres")
        String edition,

        @Size(max = 30, message = "Volume deve ter no máximo 30 caracteres")
        String volume,

        @Size(max = 100, message = "Editora deve ter no máximo 100 caracteres")
        String publisher,

        @Size(max = 50, message = "Categoria deve ter no máximo 50 caracteres")
        String category,

        @PastOrPresent(message = "A data de publicação não pode ser futura")
        LocalDate publicationDate,

        @Size(max = 255, message = "URL da imagem muito longa")
        String image,

        String gender,

        String status
) {}
