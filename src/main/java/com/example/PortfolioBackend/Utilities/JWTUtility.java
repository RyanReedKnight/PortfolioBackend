package com.example.PortfolioBackend.Utilities;

import com.example.PortfolioBackend.DTOs.AdminCredentials;
import com.example.PortfolioBackend.Exceptions.BadCredentialsException;
import com.example.PortfolioBackend.Exceptions.BadTokenException;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

// Largely based off of work done in another project
@Component
public class JWTUtility {

    @Value("${jwt.secret}")
    private String secret;
    private byte[] bytes;

    public JWTUtility() {

    }

    @PostConstruct
    private void createBytes(){
        bytes = Base64.getEncoder().encode(
                Base64.getEncoder().encode(secret.getBytes())
        );
    }

    /***
     * @return token containing the username
     * */
    public String createToken(String username, String password) {

        JwtBuilder tokenBuilder = Jwts.builder()
                .setId(username)
                .setSubject(username)
                .setIssuer("Portfolio")
                .claim("username", username)
                .setIssuedAt((new Date(System.currentTimeMillis())))
                .setExpiration(new Date(System.currentTimeMillis() + 600 * 1000))
                .signWith(new SecretKeySpec(getBytes(), "HmacSHA256"));
        return tokenBuilder.compact();

    }

    public String createToken(AdminCredentials adminCredentials) {
        return createToken(adminCredentials.getUsername(),adminCredentials.getPassword());
    }

    /**
     * @return false if token is null or empty, otherwise return true.
     * */
    public boolean isTokenValid(String token) {
        return !(token == null || token.trim().equals(""));
    }

    /**
     * @throws BadTokenException if token is null or empty.
     * @return adminUsername.
     * */
    public Optional<String> parseToken(String token) throws BadTokenException {

        if(!isTokenValid(token)) {
            throw new BadTokenException();
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Optional.of(claims.getId());
    }

    private byte[] getBytes(){
        return bytes;
    }

}
