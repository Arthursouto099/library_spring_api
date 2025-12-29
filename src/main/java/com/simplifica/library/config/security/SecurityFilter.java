package com.simplifica.library.config.security;
import com.simplifica.library.config.authentication.JwtUserData;
import com.simplifica.library.config.authentication.TokenConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private  final TokenConfig tokenConfig;
    private  final  UserDetailsService userDetailsService;

    public  SecurityFilter(TokenConfig tokenConfig, UserDetailsService userDetails) {
        this.tokenConfig = tokenConfig;
        this.userDetailsService = userDetails;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromCokie(request);

        // Caso não consiga extrair o token do cokie, tenta extrair do header
        // Posteriormente vou melhorar essa config
        if(token == null) {
            token = extractTokenBearerFromHTTPRequest(request);
        }

        if(token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Optional retorna meu token ou null
            // caso meu token seja validado ele retorna JwtUserData
            Optional<JwtUserData> optUser = tokenConfig.validateToken(token);

            if(optUser.isPresent()) {

                /*
                Pego as informações do token
                idUser,email
                */
                JwtUserData userData = optUser.get();

                // Seto o userDetails como um (User) buscado pelo username<email>
                UserDetails userDetails  = userDetailsService.loadUserByUsername(userData.email());

                // seto o authentication, como userDetail
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    // Extrai o access_token dos cokies
    private  String extractTokenFromCokie(HttpServletRequest req) {
        Cookie[] cokies = req.getCookies();
        if(cokies == null) return  null;

        for(Cookie c: cokies) {
            if("access_token".equals(c.getName())) {
                String value = c.getValue();
                return  (value == null || value.isBlank()) ? null : value;
            }
        }

        return  null;
    }

    // Extrai o bearer token do Header
    private String  extractTokenBearerFromHTTPRequest(HttpServletRequest req) {
        String authorizeHeader = req.getHeader("Authorization");

        if(Strings.isNotEmpty(authorizeHeader) && authorizeHeader.startsWith("Bearer ")) {
            String token = authorizeHeader.substring("Bearer ".length());
            return  (token.isEmpty()) ? null : token;

        }

        return  null;
    }

}
