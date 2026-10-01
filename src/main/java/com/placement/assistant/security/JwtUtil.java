package com.placement.assistant.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secret}") private String secret;
    @Value("${jwt.expiration}") private long expiration;
    private SecretKey getSigningKey(){return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));}
    public String generateToken(UserDetails u){return createToken(new HashMap<>(),u.getUsername());}
    private String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis()+expiration)).signWith(getSigningKey()).compact();
    }
    public String extractUsername(String t){return extractClaim(t,Claims::getSubject);}
    public <T> T extractClaim(String t,Function<Claims,T> r){return r.apply(extractAllClaims(t));}
    private Claims extractAllClaims(String t){
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(t).getPayload();
    }
    public boolean isTokenExpired(String t){return extractClaim(t,Claims::getExpiration).before(new Date());}
    public boolean validateToken(String t,UserDetails u){return extractUsername(t).equals(u.getUsername())&&!isTokenExpired(t);}
}
