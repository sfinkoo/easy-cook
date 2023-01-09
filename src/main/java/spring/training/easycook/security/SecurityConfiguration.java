package spring.training.easycook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.training.easycook.security.basic.BasicAuthenticationFilter;
import spring.training.easycook.security.basic.BasicUserDetailsService;
import spring.training.easycook.security.jwt.JwtAuthenticationFilter;
import spring.training.easycook.security.jwt.JwtAuthenticationProvider;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BasicAuthenticationFilter basicAuthenticationFilter;
    private final BasicUserDetailsService basicUserDetailsService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(BasicAuthenticationFilter basicAuthenticationFilter,
                                 BasicUserDetailsService basicUserDetailsService,
                                 JwtAuthenticationProvider jwtAuthenticationProvider,
                                 JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.basicAuthenticationFilter = basicAuthenticationFilter;
        this.basicUserDetailsService = basicUserDetailsService;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(basicAuthenticationFilter, jwtAuthenticationFilter.getClass())
                .authorizeRequests()
                .antMatchers("/user/create")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.userDetailsService(basicUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
