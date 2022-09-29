package com.github.vitalydev.messages.util;

import com.github.vitalydev.messages.web.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.github.vitalydev.messages.config.WebSecurity.EXPIRATION_TIME;
import static com.github.vitalydev.messages.config.WebSecurity.SECRET;

@UtilityClass
public class TokenUtil {

    public String key() {
        return TextCodec.BASE64.encode(SECRET);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        //claims.put("admin", userDetails.getAuthorities().stream().anyMatch(Role.ADMIN::equals));
        claims.put("name", ((AuthUser) userDetails).getUser().getName());
        claims.put("created", generateCurrentDate());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, key())
                .compact();
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000);
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String getToken(String header) {
        String token;
        try {
            token = header.substring(7);
        } catch (Exception e) {
            token = null;
        }
        return token;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        AuthUser user = (AuthUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !(isTokenExpired(token)));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(generateCurrentDate());
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;          // admin "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJjcmVhdGVkIjoxNjY0NTU5OTY0OTk1LCJuYW1lIjoiQWRtaW4iLCJleHAiOjI1NjQ1NTk5NjR9.inXkItOPvq92BSSZnictBNVWFuCrmOKSfqoi0jw_050VGZzgS6cXNGcQb07dY14ufUMAgMCIk9FJVXqW-aW5Ng"
        try {
            claims = Jwts.parser()
                    .setSigningKey(key())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
