package com.mindhub.homebanking.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtilService {

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public  static final long TIME_TOKEN = 1000 * 60 * 60; // 1 hour


    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims :: getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims :: getExpiration);
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken (UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        claims.put("role", role);
        return createToken(claims, userDetails.getUsername());

    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TIME_TOKEN))
                .signWith(SECRET_KEY)
                .compact();
    }
}
