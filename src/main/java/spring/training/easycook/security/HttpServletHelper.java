package spring.training.easycook.security;


import org.springframework.stereotype.Component;
import spring.training.easycook.security.jwt.JwtProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Arrays.stream;

@Component
public class HttpServletHelper {

    private static final String cookieAccessTokenName = "access_token";
    private static final String cookieRefreshTokenName = "refresh_token";

    private final JwtProperties jwtProperties;

    public HttpServletHelper(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public void putAccessCookie(HttpServletResponse response, String value) {
        response.addCookie(createSecureCookie(cookieAccessTokenName, value, jwtProperties.getExpirationAccess()));
    }

    public void putRefreshCookie(HttpServletResponse response, String value) {
        response.addCookie(createSecureCookie(cookieRefreshTokenName, value, jwtProperties.getExpirationRefresh()));
    }

    public void deleteAccessCookie(HttpServletResponse response) {
        response.addCookie(createSecureCookie(cookieAccessTokenName, null, 0));
    }

    public void deleteRefreshCookie(HttpServletResponse response) {
        response.addCookie(createSecureCookie(cookieRefreshTokenName, null, 0));
    }

    public String getAccessTokenCookie(HttpServletRequest request) {
        return stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieAccessTokenName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public String getRefreshTokenCookie(HttpServletRequest request) {
        return stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieRefreshTokenName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    private Cookie createSecureCookie(String name, String value, Integer maxAgeInMinutes) {
        var cookie = new Cookie(name, value);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInMinutes * 60);
        return cookie;
    }
}
