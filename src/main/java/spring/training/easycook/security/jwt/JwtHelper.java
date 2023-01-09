package spring.training.easycook.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spring.training.easycook.security.HttpServletHelper;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtHelper {

    private final HttpServletHelper servletHelper;
    private final JwtProperties properties;

    private final byte[] key;

    private final static Logger logger = LoggerFactory.getLogger(JwtHelper.class);

    public JwtHelper(HttpServletHelper servletHelper,
                     JwtProperties properties) {
        this.servletHelper = servletHelper;
        this.properties = properties;
        key = Base64.getDecoder().decode(properties.getSecret());
    }

    public void putAuthenticationCookie(Long userId, HttpServletResponse response) {
        JwtCredentials credentials = generateTokenPair(userId);
        servletHelper.putAccessCookie(response, credentials.getAccess());
        servletHelper.putRefreshCookie(response, credentials.getRefresh());
    }

    public JwtCredentials generateTokenPair(Long userId) {
        return JwtCredentials.builder()
                .access(generateToken(userId, properties.getExpirationAccess()))
                .refresh(generateToken(userId, properties.getExpirationRefresh()))
                .build();
    }

    public Claims parseAuthenticationToken(String token) {
        if (token == null) {
            return null;
        }

        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.debug("Exception while parsing token: {}", e.getMessage(), e);
            return null;
        }
    }

    private String generateToken(Long userId, Integer expirationInMinutes) {
        Claims claims = new DefaultClaims().setSubject(userId.toString());

        LocalDateTime now = LocalDateTime.now();
        Date issuedAt = toDate(now);
        Date expiration = toDate(now.plusMinutes(expirationInMinutes));

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public JwtPrincipal getJwtPrincipal(Claims claims) {
        return new JwtPrincipal(Long.parseLong(claims.getSubject()));
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
