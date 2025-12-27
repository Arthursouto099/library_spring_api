package com.simplifica.library.config.security;

import com.simplifica.library.config.authentication.JwtUserData;
import com.simplifica.library.config.authentication.TokenConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private  final TokenConfig tokenConfig;

    public  SecurityFilter(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizeHeader = request.getHeader("Authorization");

        if(Strings.isNotEmpty(authorizeHeader) && authorizeHeader.startsWith("Bearer ")) {
            String token = authorizeHeader.substring("Bearer ".length());
            Optional<JwtUserData> optUser = tokenConfig.validateToken(token);

            if(optUser.isPresent()) {
                JwtUserData userData = optUser.get();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userData, null, null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        }

        else {
            filterChain.doFilter(request, response);
        }
    }

}
