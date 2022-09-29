package com.github.vitalydev.messages.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;

import static com.github.vitalydev.messages.config.WebSecurity.SECRET;

public class JwtUtil {

    public static String key(String key) {
        return TextCodec.BASE64.encode(key);
    }

    public static String getUserLoginNameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key(SECRET))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

      /*  String user = Jwts.parser()
               // .setSigningKey(SECRET)
                .setSigningKey(key(SECRET))
               // .parseClaimsJws(authResponse.getToken().replace(TOKEN_PREFIX, ""))
                .parseClaimsJws(authResponse.getToken())
                .getBody()
                .getSubject();*/
}
