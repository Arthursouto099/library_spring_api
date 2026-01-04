package com.simplifica.library.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "id_user")
    private  Long idUser;

    @Column(nullable = false, unique = true)
    private  String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private  String password;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Label> labels = new ArrayList<>();

    public void addLabelToLabelList(Label label) {
        if (this.labels == null) {
            this.labels = new ArrayList<>();
        }

        this.labels.add(label);
        label.setUser(this);
    }
    /*
    User possui (n) livros
    Book possui (1) User
     */
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Book> books = new ArrayList<>();

    public void addBookToList(Book book) {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }

        this.books.add(book);
        book.setUser(this);
    }

    public void removeBookToList(Book book) {
        this.books.remove(book);
        book.setUser(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
