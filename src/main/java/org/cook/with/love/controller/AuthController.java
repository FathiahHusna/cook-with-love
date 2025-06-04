package org.cook.with.love.controller;

import lombok.extern.slf4j.Slf4j;
import org.cook.with.love.auth.JwtUtil;
import org.cook.with.love.constant.CookLoveConstant;
import org.cook.with.love.dto.JwtDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    @Value("${org.cook.with.love.api.key:}")
    private String apiKeyValue;

    @Value("${org.cook.with.love.api.secret:}")
    private String apiSecretValue;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/get-token")
    public ResponseEntity<?> getToken(@RequestHeader(CookLoveConstant.API_KEY_HEADER) String apiKey,
                                      @RequestHeader(CookLoveConstant.API_SECRET_HEADER) String apiSecret) {
        log.trace("[getToken] Start getToken");
        if (apiKeyValue.equals(apiKey) && apiSecretValue.equals(apiSecret)) {
            JwtDTO res = jwtUtil.generateToken(apiKey);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        log.trace("[refreshToken] Start refreshToken");
        String token = authHeader.replace("Bearer ", "");
        if (jwtUtil.isTokenValid(token)) {
            String username = jwtUtil.extractUsername(token);
            JwtDTO res = jwtUtil.generateToken(username);
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

}
