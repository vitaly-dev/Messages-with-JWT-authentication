package com.github.vitalydev.messages.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*@Configuration
@PropertySource("classpath:token/token.properties")*/
public class TokenUtil {

 /*   @Autowired
    private Environment env;

    private String key() {
        String key = env.getProperty("token.key");
        return TextCodec.BASE64.encode(key);
    }

    private Long expiration() {
        return Long.parseLong(env.getProperty("token.expiration"));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getName());
        claims.put("admin", userDetails.getAuthorities().stream().anyMatch(Role.ROLE_ADMIN::equals));
        claims.put("name", ((LoggedUser) userDetails).getUserTo().getName());
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
        return new Date(System.currentTimeMillis() + expiration() * 1000);
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
        LoggedUser user = (LoggedUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getName()) && !(isTokenExpired(token)));
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
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }*/
}
