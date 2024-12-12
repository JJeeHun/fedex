package com.example.fedex_json.fedex.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter @Setter @ToString
public class OAuthResponse {

    private String token_type;
    private Integer expires_in;
    private String scope;
    private String access_token;

    private final LocalDateTime createdAt = LocalDateTime.now();
    private final Integer DIFF_EXPIRED = 10; // 요청 시간 10초 정도

    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);
        return duration.getSeconds() >= (this.expires_in - DIFF_EXPIRED);
    }
}
