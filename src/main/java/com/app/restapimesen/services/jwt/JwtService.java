package com.app.restapimesen.services.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    String generateToken(
            Map<String, Objects> extraClaims,
            UserDetails userDetails
    );

    boolean isTokenValid(String token, UserDetails userDetails);

    String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            Date expiration
    );

    Claims extractAllClaims(String token);

    Key getSignInKey();

    <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    );
}
