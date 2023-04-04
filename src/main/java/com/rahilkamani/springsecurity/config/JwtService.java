package com.rahilkamani.springsecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.spec.EdECPrivateKeySpec;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {


    private static final String jwtSecretKey = "24423F4528482B4D6251655468576D5A7134743777217A25432A46294A404E63";

    public String extractUserName(String jwtToken) {
        return extractClaim(jwtToken,Claims::getSubject);
    }

    public String generateJwtToken(Map<String, Object> claims, UserDetails userDetails){

        return Jwts.builder().
                setClaims(claims).
                setSubject(userDetails.getUsername()).
                setIssuedAt(new Date(System.currentTimeMillis())
                ).setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateJwtToken(UserDetails userDetails){
        return generateJwtToken(new HashMap<>(),userDetails);
    }

    public boolean isTokenValid(String jwtToken,UserDetails userDetails){
        final String userName = extractUserName(jwtToken);
        return (userName.equalsIgnoreCase(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    public boolean isTokenExpired(String jwtToken){
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken,Claims::getExpiration);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims,T> claimResolver){

        final Claims claim = getAllClaims(jwtToken);
        return claimResolver.apply(claim);
    }

    public Claims getAllClaims(String jwtToken){

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
