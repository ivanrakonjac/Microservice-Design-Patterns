package com.jwt.security.configuration;

import com.jwt.security.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT utility service
 */

@Service
public class JwtService {

    // This is secret key, which we use for signing generated tokens
    // Key is generated online
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    private static final long JWT_EXPIRATION_TIME_IN_MINUTES = 8;

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserEntity userEntity){
        return generateToken(populateExtraClaims(userEntity), userEntity);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * JWT_EXPIRATION_TIME_IN_MINUTES) ))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Check does the token has correct username (same as it is passed as a param)
     * Check is token expired
     *
     * @param token user token
     * @param userDetails user details
     * @return boolean
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Map<String, Object> populateExtraClaims(UserEntity userEntity){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0));
        extraClaims.put("firstName", userEntity.getFirstName());
        extraClaims.put("lastName", userEntity.getLastName());
        return extraClaims;
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
