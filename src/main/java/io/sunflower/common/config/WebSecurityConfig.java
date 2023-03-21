package io.sunflower.common.config;

import io.sunflower.common.util.RedisUtil;
import io.sunflower.security.jwt.JwtAuthFilter;
import io.sunflower.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(PERMIT_URL_ARRAY).permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/posts/**").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/update-passwords/**").permitAll()
                .antMatchers("/reissue-passwords/**").permitAll()
                .antMatchers("/search-nutritions/**").permitAll()
                .antMatchers("/search-posts/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtTokenProvider, redisUtil), UsernamePasswordAuthenticationFilter.class);

//        http.exceptionHandling().accessDeniedPage("/api/user/forbidden");

        return http.build();
    }
}