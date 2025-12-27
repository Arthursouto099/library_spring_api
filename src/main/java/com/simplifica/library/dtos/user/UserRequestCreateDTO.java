package com.simplifica.library.dtos.user;

import com.simplifica.library.entities.User;

public record UserRequestCreateDTO(
        // possível validação futura
        String name,
        String email,
        String password
) {


    public  User toEntity() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return  user;
    };
}
