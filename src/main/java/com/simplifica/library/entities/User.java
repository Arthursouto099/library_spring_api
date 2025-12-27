package com.simplifica.library.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class User {
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
}
