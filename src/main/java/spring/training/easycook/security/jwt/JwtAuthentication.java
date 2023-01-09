package spring.training.easycook.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

@Getter
@Setter
public class JwtAuthentication extends AbstractAuthenticationToken {

    private final JwtCredentials credentials;
    private JwtPrincipal principal;

    public JwtAuthentication(JwtCredentials credentials) {
        super(Collections.emptyList());
        this.credentials = credentials;
    }
}
