package spring.training.easycook.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import spring.training.easycook.security.HttpServletHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationHelper {

    private final JwtHelper jwtHelper;
    private final HttpServletHelper servletHelper;

    public JwtAuthenticationHelper(JwtHelper jwtHelper,
                                   HttpServletHelper servletHelper) {
        this.jwtHelper = jwtHelper;
        this.servletHelper = servletHelper;
    }

    public JwtAuthentication getAuthenticationRequest(HttpServletRequest request) {
        String accessToken = servletHelper.getAccessTokenCookie(request);
        String refreshToken = servletHelper.getRefreshTokenCookie(request);

        if (accessToken == null && refreshToken == null) {
            return null;
        }

        JwtCredentials credentials = new JwtCredentials(accessToken, refreshToken);
        return new JwtAuthentication(credentials);
    }

    public void updateAuthenticationCookieIfRequired(JwtAuthentication authResult, HttpServletResponse response) {
        Claims accessClaims = jwtHelper.parseAuthenticationToken(authResult.getCredentials().getAccess());
        if (accessClaims != null) {
            return;
        }

        JwtPrincipal principal = authResult.getPrincipal();
        putAuthenticationCookies(principal.getUserId(), response);
    }

    public void putAuthenticationCookies(Long userId, HttpServletResponse response) {
        JwtCredentials jwtCredentials = jwtHelper.generateTokenPair(userId);
        servletHelper.putAccessCookie(response, jwtCredentials.getAccess());
        servletHelper.putRefreshCookie(response, jwtCredentials.getRefresh());
    }
}
