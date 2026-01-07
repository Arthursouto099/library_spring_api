package com.simplifica.library.controllers;


import com.simplifica.library.dtos.book.requests.BookCreateRequestDTO;
import com.simplifica.library.dtos.book.requests.BookUpdateRequestDTO;
import com.simplifica.library.dtos.book.responses.BookResponseDTO;
import com.simplifica.library.entities.Book;
import com.simplifica.library.entities.Label;
import com.simplifica.library.entities.User;
import com.simplifica.library.services.BookService;
import com.simplifica.library.services.LabelService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book/features")
public class BookController {
    private  final  BookService bookService;
    private final LabelService labelService;

    public  BookController(BookService bookService, LabelService labelService) {
        this.bookService = bookService;
        this.labelService = labelService;
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

    @PatchMapping("/add/{idLabel}/{idBook}")
    public  ResponseEntity<BookResponseDTO> addLabel(
            @PathVariable Long idLabel,
            @PathVariable Long idBook

    ) {
        Label label = labelService.getLabelByIdLabel(idLabel);
        Book book = bookService.findByIdBook(idBook);
        Book updated = bookService.addLabel(label, book);
        return  ResponseEntity.ok(BookResponseDTO.fromEntity(updated));
    }

    @PatchMapping("/remove/{idLabel}/{idBook}")
    public  ResponseEntity<BookResponseDTO> removeLabel(
            @PathVariable Long idLabel,
            @PathVariable Long idBook

    ) {
        Label label = labelService.getLabelByIdLabel(idLabel);
        Book book = bookService.findByIdBook(idBook);
        Book updated = bookService.removeLabel(label, book);
        return  ResponseEntity.ok(BookResponseDTO.fromEntity(updated));
    }

    @PatchMapping("/edit/{idBook}")
    public  ResponseEntity<BookResponseDTO> editBook(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody BookUpdateRequestDTO req,
            @PathVariable Long idBook
    ) {
        Book book = bookService.editBook(idBook, user.getIdUser(), req);
        return  ResponseEntity.status(HttpStatus.OK).body(BookResponseDTO.fromEntity(book));
    }

    // futura inplementação do user na hora de deletar
    @DeleteMapping("/delete/{idBook}")
    public ResponseEntity<?> deleteBook(
            @PathVariable Long idBook
    ){
        bookService.deleteBook(idBook);
        return  ResponseEntity.noContent().build();
    }
}
