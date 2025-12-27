package com.simplifica.library.exceptions;

import com.simplifica.library.dtos.errors.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Vai pegar todas a exceções do tipo ResourceNotFoundException
    // retorna resposta padronizada
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
       ErrorResponseDTO e = new ErrorResponseDTO(
               404,
               "RECURSO NÃO ENCONTRADO",
               ex.getMessage(),
               req.getRequestURI(),
               LocalDateTime.now()
       );
       return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
    }

    @ExceptionHandler(UnauthorizedResourceException.class)
    public ResponseEntity<ErrorResponseDTO> UnauthorizedHandle(UnauthorizedResourceException ex, HttpServletRequest req) {
       ErrorResponseDTO e = new ErrorResponseDTO(
               401,
               "RECURSO NÃO AUTORIZADO",
               ex.getMessage(),
               req.getRequestURI(),
               LocalDateTime.now()
       );
       return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
    }
}
