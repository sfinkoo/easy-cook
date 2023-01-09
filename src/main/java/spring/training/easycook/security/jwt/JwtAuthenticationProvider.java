package spring.training.easycook.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtHelper jwtHelper;

    public JwtAuthenticationProvider(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    public JwtAuthentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication token = (JwtAuthentication) authentication;
        String accessToken = token.getCredentials().getAccess();
        String refreshToken = token.getCredentials().getRefresh();

        Claims accessClaims = jwtHelper.parseAuthenticationToken(accessToken);
        if (accessClaims != null) return succeedAuthentication(token, accessClaims);

        Claims refreshClaims = jwtHelper.parseAuthenticationToken(refreshToken);
        if (refreshClaims == null) {
            throw new BadCredentialsException("Both access and refresh token was expired");
        }

        return succeedAuthentication(token, refreshClaims);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }

    private JwtAuthentication succeedAuthentication(JwtAuthentication token, Claims claims) {
        token.setAuthenticated(true);
        token.setPrincipal(jwtHelper.getJwtPrincipal(claims));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(token);
        SecurityContextHolder.setContext(securityContext);

        return token;
    }
}
