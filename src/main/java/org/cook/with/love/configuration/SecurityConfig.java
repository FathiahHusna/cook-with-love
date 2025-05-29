package org.cook.with.love.configuration;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.cook.with.love.auth.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${org.cook.with.love.api.key:}")
    private String apiKeyValue;

    @Value("${org.cook.with.love.api.secret:}")
    private String apiSecretValue;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        ApiKeyAuthFilter filter = new ApiKeyAuthFilter(new AntPathRequestMatcher("/api/v1/**"));

        filter.setAuthenticationManager(authentication -> {
            String apiKey = (String) authentication.getPrincipal();
            String apiSecret = (String) authentication.getCredentials();

            log.trace("[securityFilterChain] Start securityFilterChain");
            log.trace("[securityFilterChain] Receive API key {}" , apiKey);
            log.trace("[securityFilterChain] Receive API secret {}" , apiSecret);

            if (apiKeyValue.equals(apiKey) && apiSecretValue.equals(apiSecret)) {
                return new ApiKeyAuthenticationToken(apiKey, apiSecret, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            } else {
                throw new BadCredentialsException("Invalid API Key or Secret");
            }
        });

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"))
                )

                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/recipe/**").authenticated() // protect everything else
                        .requestMatchers("/api/v1/auth/refresh-token").authenticated() // protect everything else
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/v1/auth/get-token").permitAll() // 👈 allow token generation without auth
                        .anyRequest().permitAll()
                )
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable) // 👈 prevent 302 redirects
                .httpBasic(AbstractHttpConfigurer::disable); // also disable basic login


        return http.build();
    }

}
