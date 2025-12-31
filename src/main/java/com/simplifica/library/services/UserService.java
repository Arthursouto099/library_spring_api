package com.simplifica.library.services;

import com.simplifica.library.dtos.user.requests.UserRequestUpdateDTO;
import com.simplifica.library.entities.User;
import com.simplifica.library.exceptions.ResourceNotFoundException;
import com.simplifica.library.exceptions.UnauthorizedResourceException;
import com.simplifica.library.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
public class UserService {

    private  final UserRepository userRepository;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;

    public  UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public User createUser(User user) {
        // fazer o hash da senha
        String hashPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return  userRepository.save(user);
    }


    public  User findByEmail(String email) {
        return  userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));

    }

    @Transactional
    public  User editUser(String email, UserRequestUpdateDTO req) {
        User u = this.findByEmail(email);

        if (req.name() != null && !req.name().isBlank()) {
            u.setName(req.name().trim());
        }

        if(req.email() != null && !req.email().isBlank()) {
            String normalizeEmail = req.email().toLowerCase().trim();
            if(!normalizeEmail.equals(u.getEmail()) && userRepository.existsByEmail(normalizeEmail)) {
                throw new ResourceNotFoundException("Esse email já está em uso");
            }
            u.setEmail(normalizeEmail);
        }

        return userRepository.save(u);
    }

    @Transactional
    public void deleteUser(String email)  {
        User u = this.findByEmail(email);
        userRepository.delete(u);
    };



}
