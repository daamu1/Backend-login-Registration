package org.app.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.app.constant.Constant;
import org.app.enums.UserRole;
import org.app.model.AppUser;
import org.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final UserRepository userRepository;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(AppUser userDetails, UserRole userRole) {
        return generateToken(new HashMap<>(), userDetails, userRole);
    }


    public String generateToken(
            Map<String,
                    Object> extraClaims,
            AppUser userDetails,
            UserRole userRole
    ) {
        return buildToken(
                extraClaims,
                userDetails,
                userRole,
                jwtExpiration);
    }







    private String buildToken(
            Map<String, Object> extraClaims,
            AppUser userDetails,
            UserRole userRole,
            long expiration
    ) {
        Claims claims = Jwts.claims();
        claims.setSubject(userDetails.getUsername());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() + expiration));
        claims.put(Constant.AUTHORITIES_CLAIM, getUserAuthorities(userDetails));
//        claims.put(Constant.USERNAME, userDetails.getName());
        claims.put(Constant.ROLE, userRole);
        // adding custom claim for the user's email
        if (extraClaims != null && !extraClaims.isEmpty()) {
            claims.putAll(extraClaims);
        }

        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenValid(String token, AppUser userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes =
                Decoders
                        .BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public List<GrantedAuthority> getAuthoritiesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        // get the roles claim as a comma-separated string
        String rolesClaim = claims.get(Constant.AUTHORITIES_CLAIM, String.class);

        if (rolesClaim != null) {
            return Arrays.stream(rolesClaim.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


    public String getNameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        // extract the "name" claim from the JWT token
        return claims.get(Constant.USERNAME, String.class);
    }


    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        // extract the "role" claim from the JWT token
        return claims.get(Constant.ROLE, String.class);
    }

    private String getUserAuthorities(UserDetails user) {
        return user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
