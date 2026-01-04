package com.simplifica.library.repositories;

import com.simplifica.library.entities.Book;
import com.simplifica.library.entities.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findByUserIdUser(Long idUser);
}
