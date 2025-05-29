package org.cook.with.love.dto;

import lombok.Data;

@Data
public class JwtDTO {

    private String token;
    private long expiresAt; // epoch milliseconds

}
