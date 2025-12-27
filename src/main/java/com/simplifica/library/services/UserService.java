package com.simplifica.library.services;

import com.simplifica.library.entities.User;
import com.simplifica.library.exceptions.ResourceNotFoundException;
import com.simplifica.library.exceptions.UnauthorizedResourceException;
import com.simplifica.library.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class UserService {

    private  final UserRepository userRepository;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;

    public  UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(User user) {
        // fazer o hash da senha
        String hashPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return  userRepository.save(user);
    }

    public  User sign(String email, String password) {
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));

        if(!bCryptPasswordEncoder.matches(password, u.getPassword())) {
            throw  new UnauthorizedResourceException("Senha inválida");
        }

        return u;
    }


    public  User findById(Long id) {
        return  userRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Usuario não encontrado"));

    }



}
