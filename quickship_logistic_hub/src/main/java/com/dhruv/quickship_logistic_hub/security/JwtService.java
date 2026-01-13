package com.dhruv.quickship_logistic_hub.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

import static io.jsonwebtoken.Jwts.*;

@Service
public class JwtService {
    public final String issuer;
    public final int expMinutes;
    public final Key key;


    public JwtService(
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.expMinutes}") int expMinutes,
            @Value("${app.jwt.secret}") String secret
    ) {
        this.issuer = issuer;
        this.expMinutes = expMinutes;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userName, List<String> roles){
        Date now = new Date();
        Date exp = new Date(now.getTime() + expMinutes  * 60L * 1000L);

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return builder()
                .setIssuer(issuer)
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parseAndValidate(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(key)
                .requireIssuer(issuer)
                .build()
                .parseClaimsJws(token);
    }
}