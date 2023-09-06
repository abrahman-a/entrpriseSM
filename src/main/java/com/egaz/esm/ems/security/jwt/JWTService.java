package com.egaz.esm.ems.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class JWTService {

    @Value("${spring.jwt.secret}")
    private  String JWT_SECRET;

    @Value("${spring.jwt.jwtExpirationInMs}")
    private int JWT_EXPIRATION_TIME_IN_MILLISECONDS;

    public String generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();
        return tokenCreator(claims, userName);
    }



    public String tokenCreator(Map<String, Object> claims, String userName){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION_TIME_IN_MILLISECONDS))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUsernameFromToken(String theToken){
        return extractClaim(theToken, Claims ::getSubject);
    }
    public Date extractExpirationTimeFromToken(String theToken) {
        return extractClaim(theToken, Claims :: getExpiration);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private boolean isTokenExpired(String theToken) {
        return extractExpirationTimeFromToken(theToken).before(new Date());
    }

    private Key getSignedKey(){
        byte[] keyByte = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyByte);
    }


}
