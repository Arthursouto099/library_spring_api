package com.simplifica.library.config.authentication;


import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CokieConfig {

    private final String COKIE_KEY = "access_token";

    public ResponseCookie generateCokie(String token) {
        return ResponseCookie.from(COKIE_KEY, token)
                .httpOnly(true)
                .secure(false)
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(TokenConfig.getJWTExpiresHours()))
                .build();
    }
}
