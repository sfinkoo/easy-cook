package spring.training.easycook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import spring.training.easycook.security.jwt.JwtProperties;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties({
        JwtProperties.class,
})
public class EasyCookApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCookApplication.class, args);
    }

}
