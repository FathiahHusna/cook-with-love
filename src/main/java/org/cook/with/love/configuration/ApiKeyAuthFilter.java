package org.cook.with.love.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.cook.with.love.constant.CookLoveConstant;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

/*
 * Reference: https://www.geeksforgeeks.org/securing-spring-boot-api-with-api-key-and-secret/
 * This filter class will attempt to the authenticate the request by creating an instance of ApiKeyAuthenticationToken and passing it to authentication manager.
 */
@Slf4j
public class ApiKeyAuthFilter extends AbstractAuthenticationProcessingFilter {


    public ApiKeyAuthFilter(RequestMatcher requiresAuth) {
        super(requiresAuth);
        log.trace("[constructor] ApiKeyAuthFilter created for " + requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String apiKey = request.getHeader(CookLoveConstant.API_KEY_HEADER);
        String apiSecret = request.getHeader(CookLoveConstant.API_SECRET_HEADER);

        log.trace("[attemptAuthentication] Start attemptAuthentication");
        log.trace("[attemptAuthentication] Request URI: {}", request.getRequestURI());
        log.trace("[attemptAuthentication] Check apiKey: {}", apiKey);
        log.trace("[attemptAuthentication] Check apiSecret: {}", apiSecret);
        if (apiKey == null || apiSecret == null) {
            throw new BadCredentialsException("Missing API Key or Secret");
        }

        Authentication auth = new ApiKeyAuthenticationToken(apiKey, apiSecret);
        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.trace("[successfulAuthentication] Start successfulAuthentication");
        log.trace("[successfulAuthentication] Check uri {}" , request.getRequestURI());
        log.trace("[successfulAuthentication] Check principal {}" , authResult.getPrincipal());
        log.trace("[successfulAuthentication] Check credentials {}" , authResult.getCredentials());
        // Set authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authResult);  // Ensure Spring Security knows this is an authenticated user

        // call when using session-based authentication
//         super.successfulAuthentication(request, response, chain, authResult);

        // Continue filter chain
        chain.doFilter(request, response);
    }

}
