package com.simplifica.library.config.authentication;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.simplifica.library.entities.User;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {

    private static final long JWT_EXPIRES_HOURS = 2;;
    private static final String SECRET_KEY = "Secret";


    public static Long getJWTExpiresHours() {
        return  JWT_EXPIRES_HOURS;
    }

    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create().
                withClaim("userId", user.getIdUser())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(JWT_EXPIRES_HOURS)))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JwtUserData> validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            DecodedJWT decode = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return  Optional.of(JwtUserData.builder()
                    .userId(decode.getClaim("userId").asLong())
                    .email(decode.getSubject())
                    .build()
            );
        }

        catch (JWTVerificationException ex) {
            return  Optional.empty();
        }
    }
}
