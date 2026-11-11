package com.ecommerce.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;
    private final long jwtExpiration;

    public JwtService(
            @Value("${jwt.private-key-path:classpath:keys/newKey/private_key.pem}") RSAPrivateKey privateKey,
            @Value("${jwt.public-key-path:classpath:keys/newKey/public_key.pem}") RSAPublicKey publicKey,
            @Value("${jwt.expiration-ms:86400000}") long jwtExpiration) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.jwtExpiration = jwtExpiration;
    }


    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> payload, UserDetails userDetails) {
        return Jwts.builder()
                .claims(payload)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public Claims validateAndParseJwt(String jwtString) {
        var jws = Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(jwtString);

        if (!"RS256".equals(jws.getHeader().getAlgorithm())) {
            throw new IllegalArgumentException("Algoritmo JWT non valido");
        }

        Claims claims = jws.getPayload();


        if (claims.getExpiration() != null && claims.getExpiration().before(new Date())) {
            throw new IllegalArgumentException("Token JWT scaduto");
        }
        return claims;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
