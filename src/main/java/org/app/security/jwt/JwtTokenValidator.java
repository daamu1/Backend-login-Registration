package org.app.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.constant.Constant;
import org.app.exception.TokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenValidator {
    @Value("${application.security.jwt.secret-key}")
    private String jwtSecret;

    public void validateToken(String authToken) throws TokenException {
        try {
            // Convert the secret string into a SecretKey object
            SecretKey signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            // Parse and validate JWT
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();
            claims.getExpiration();
            // No need to manually check expiration here because JJWT does it automatically

        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new TokenException(Constant.INCORRECT_JWT_SIGNATURE_MSZ);

        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new TokenException(Constant.MALFORMED_JWT_TOKEN_MSZ);

        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new TokenException(Constant.EXPIRED_JWT_TOKEN_MSZ);

        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new TokenException(Constant.UNSUPPORTED_JWT_TOKEN_MSZ);

        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new TokenException(Constant.ILLEGAL_JWT_TOKEN_MSZ);
        }
    }
}
