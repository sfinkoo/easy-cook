package spring.training.easycook.security.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.stereotype.Component;
import spring.training.easycook.security.jwt.JwtHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BasicAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationConverter authenticationConverter;
    private final JwtHelper jwtHelper;

    public BasicAuthenticationFilter(JwtHelper jwtHelper) {
        super("/authenticate");
        this.authenticationConverter = new BasicAuthenticationConverter();
        this.jwtHelper = jwtHelper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = authenticationConverter.convert(request);
        if (authentication == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        BasicUserDetails userDetails = (BasicUserDetails) authResult.getPrincipal();
        jwtHelper.putAuthenticationCookie(userDetails.getId(), response);
    }

    @Autowired
    @Lazy
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
