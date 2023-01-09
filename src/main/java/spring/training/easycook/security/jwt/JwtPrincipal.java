package spring.training.easycook.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtPrincipal {

    private final Long userId;
}
