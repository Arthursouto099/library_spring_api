package com.simplifica.library.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id_book")
    private Long idBook;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String edition;

    @Column(nullable = false)
    private String volume;

    @Column(nullable = false)
    private  String publisher;

    @Column(nullable = false)
    private  String category;

    @Column(nullable = false)
    private LocalDate publicationDate;

    @Column()
    private String image;

    @Column
    private  String gender;

    @Column()
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @PrePersist
    public  void prePersist() {
        if(this.category == null) this.category = "Sem categoria";
        if(this.volume == null) this.volume = "Único";
        if(this.edition == null) this.edition = "1 edição";
        if(this.publicationDate == null) this.publicationDate = LocalDate.now();
    }

    @ManyToMany
    @JoinTable(
            name = "book_label",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels = new HashSet<>();
    
    public void addLabel(Label label) {
        this.labels.add(label);
        label.getBooks().add(this);
    }

    public  void removeLabel(Label label) {
        this.labels.remove(label);
        label.getBooks().remove(this);
    }
}
