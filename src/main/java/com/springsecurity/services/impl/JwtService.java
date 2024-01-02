package com.springsecurity.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.springsecurity.entities.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${token-secret-key}")
  private String SECRET_KEY;

  public String generateToken(UserEntity user) {
    return getToken(new HashMap<>(), user);
  }

  private String getToken(Map<String, Object> extraClaims, UserEntity user) {

    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(user.getEmail())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String getUserNameFromToken(String token) {
    return null;
  }

  public boolean isValidToken(String token, UserDetails user) {
    String userEmail = extractUserName(token);
    return (userEmail.equals(user.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    Date expiration = extractExpiration(token);
    return expiration.before(new Date());
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);

    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {

    JwtParser parser = (JwtParser) Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build();

    Claims body = (Claims) parser.parse(token).getBody();
    return body;
  }

}
