package com.simplifica.library.repositories;

import com.simplifica.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<Book, Long> {}
