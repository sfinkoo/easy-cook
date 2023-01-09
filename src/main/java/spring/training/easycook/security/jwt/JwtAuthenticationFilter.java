package spring.training.easycook.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtAuthenticationHelper jwtAuthHelper;
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JwtAuthenticationHelper jwtAuthHelper) {
        this.jwtAuthHelper = jwtAuthHelper;
    }

    @Lazy
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            JwtAuthentication authRequest = jwtAuthHelper.getAuthenticationRequest(request);
            if (authRequest == null) {
                return;
            }

            Authentication authResult = authenticationManager.authenticate(authRequest);

            JwtAuthentication jwtAuthentication = (JwtAuthentication) authResult;
            jwtAuthHelper.updateAuthenticationCookieIfRequired(jwtAuthentication, response);
        } catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
