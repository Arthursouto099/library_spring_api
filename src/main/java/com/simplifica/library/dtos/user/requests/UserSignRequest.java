package com.simplifica.library.dtos.user.requests;


import jakarta.validation.constraints.NotEmpty;

public record UserSignRequest(@NotEmpty(message = "Email é obrigatorio") String email, @NotEmpty(message = "Password é obrigatório") String password) { }
