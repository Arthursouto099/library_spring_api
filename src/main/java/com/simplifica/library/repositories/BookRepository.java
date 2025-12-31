package com.simplifica.library.repositories;

import com.simplifica.library.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book, Long> {
    Page<Book> findByUserIdUser(Long idUser, Pageable pageable);
    boolean existsById(long idBook);
    Optional<Book> findByIdBookAndUserIdUser(Long idBook, Long idUser);


}
