package com.simplifica.library.controllers;


import com.simplifica.library.dtos.book.requests.BookCreateRequestDTO;
import com.simplifica.library.dtos.book.responses.BookResponseDTO;
import com.simplifica.library.entities.Book;
import com.simplifica.library.entities.User;
import com.simplifica.library.services.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book/features")
public class BookController {
    private  final  BookService bookService;

    public  BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<BookResponseDTO> createBook(@AuthenticationPrincipal User user,
    @Valid  @RequestBody BookCreateRequestDTO bookCreateRequestDTO
    ) {
    Book book = bookService.createBook(bookCreateRequestDTO.toEntity(), user);
    return ResponseEntity.status(HttpStatus.CREATED).body(BookResponseDTO.fromEntity(book));
    }

    @GetMapping("/all")
    public  ResponseEntity<Page<BookResponseDTO>> findAllByUser(
            @AuthenticationPrincipal User user,
            Pageable pageable
    ) {
        Page<Book> books = bookService.findByIdUser(user.getIdUser(), pageable);
        return ResponseEntity.ok(books.map(BookResponseDTO::fromEntity));
    }

    @PatchMapping("/edit/{idBook}")
    public  ResponseEntity<BookResponseDTO> editBook(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody BookCreateRequestDTO req,
            @PathVariable Long idBook
    ) {
        Book book = bookService.editBook(idBook, user.getIdUser(), req);
        return  ResponseEntity.status(HttpStatus.OK).body(BookResponseDTO.fromEntity(book));
    }

    // futura inplementação do user na hora de deletar
    @DeleteMapping("/delete/{idBook}")
    public ResponseEntity<?> deleteBook(
            @AuthenticationPrincipal User user,
            @PathVariable Long idBook
    ){
        bookService.deleteBook(idBook);
        return  ResponseEntity.noContent().build();
    }
}
