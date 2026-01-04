package com.simplifica.library.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_label")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "id_label")
    long idLabel;

    @Column(nullable = false)
    String name;

    @ManyToOne()
    @JoinColumn(name = "id_user", nullable = false)
    User user;

    @ManyToMany(mappedBy = "labels")
    Set<Book> books = new HashSet<>();
}
