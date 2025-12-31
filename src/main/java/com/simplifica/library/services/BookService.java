package com.simplifica.library.services;


import com.simplifica.library.dtos.book.requests.BookUpdateRequestDTO;
import com.simplifica.library.entities.Book;
import com.simplifica.library.entities.User;
import com.simplifica.library.exceptions.ResourceNotFoundException;
import com.simplifica.library.repositories.BookRepository;
import com.simplifica.library.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.function.Consumer;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public  Page<Book> findByIdUser(Long idUser, Pageable pageable) {
        return  bookRepository.findByUserIdUser(idUser, pageable);
    }

    public Book findByIdBook(Long bookId) {
        return  bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
    }

    public Book findByIdAndIdUser(Long bookId, Long idUser) {
        return  bookRepository.findByIdBookAndUserIdUser(bookId, idUser).orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
    }

    @Transactional
    public Book createBook(Book book, User user) {
        User managedUser = userRepository.getReferenceById(user.getIdUser());
        managedUser.addBookToList(book);
        userRepository.save(managedUser);
        return  book;
    }


    private void validValue(Consumer<String> setter, String value) {
        if(value != null && !value.isBlank()) {
            setter.accept(value);
        }
    }


    @Transactional
    public  Book editBook(Long idBook, Long idUser, BookUpdateRequestDTO req) {
        Book book = findByIdAndIdUser(idBook, idUser);

        validValue(book::setTitle, req.title());
        validValue(book::setAuthor, req.author());
        validValue(book::setEdition, req.edition());
        validValue(book::setVolume, req.volume());
        validValue(book::setPublisher, req.publisher());
        validValue(book::setCategory, req.category());
        validValue(book::setImage, req.image());
        validValue(book::setGender, req.gender());
        validValue(book::setStatus, req.status());

        if(req.publicationDate() != null && !req.publicationDate().isAfter(LocalDate.now())) {
            book.setPublicationDate(req.publicationDate());
        }

        return  bookRepository.save(book);
    }


    @Transactional
    public  void  deleteBook(Long idBook) {
        if(!bookRepository.existsById(idBook)) {
            throw  new ResourceNotFoundException("Lirvo não encontrado");
        }
        bookRepository.deleteById(idBook);
    }





}
