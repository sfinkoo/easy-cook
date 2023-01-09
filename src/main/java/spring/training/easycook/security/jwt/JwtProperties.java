package spring.training.easycook.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private Integer expirationAccess;

    private Integer expirationRefresh;

    private String secret;

    public Integer getExpirationAccess() {
        return expirationAccess;
    }

    public void setExpirationAccess(Integer expirationAccess) {
        this.expirationAccess = expirationAccess;
    }

    public Integer getExpirationRefresh() {
        return expirationRefresh;
    }

    public void setExpirationRefresh(Integer expirationRefresh) {
        this.expirationRefresh = expirationRefresh;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
