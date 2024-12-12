package com.example.fedex_json.fedex.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
public class OAuthRequest {
    private final static String TEST_URL = "https://apis-sandbox.fedex.com/oauth/token";
    private final static String PROD_URL = "https://apis.fedex.com/oauth/token";
    private final String URL;
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String OAUTH_REQUEST_BODY;
    private OAuthResponse O_AUTH_RESPONSE;
    private boolean isProd;

    public OAuthRequest(String clientId, String clientSecret, String mode) {
        this.CLIENT_ID = clientId;
        this.CLIENT_SECRET = clientSecret;
        this.OAUTH_REQUEST_BODY = String.format("grant_type=client_credentials&client_id=%s&client_secret=%s", CLIENT_ID, CLIENT_SECRET);

        isProd = "prod".equals(mode);
        this.URL = isProd ? PROD_URL : TEST_URL;
    }

    public String getToken() {

        if(O_AUTH_RESPONSE != null && !O_AUTH_RESPONSE.isExpired())
            return O_AUTH_RESPONSE.getAccess_token();

        RestTemplate restTemplate = new RestTemplate();

        // Prepare request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(OAUTH_REQUEST_BODY, headers);

        try {
            // Make POST request
            ResponseEntity<OAuthResponse> response = restTemplate.postForEntity(
                    URL,
                    request,
                    OAuthResponse.class
            );

            log.info("Fedex oAuth 토큰 발급 여부 [ status = {}, body = {} ]", response.getStatusCode(), response.getBody());

            // Extract access_token
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                O_AUTH_RESPONSE = response.getBody();
                return O_AUTH_RESPONSE.getAccess_token();
            } else {
                throw new IllegalArgumentException("Failed to get access token. Response: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("OAuth 2.0 token retrieval failed", e);
        }
    }

    public HttpHeaders getHeader() {
        String token = this.getToken();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("authorization", "Bearer " + token);
        return httpHeaders;
    }

    public boolean isProd() {
        return isProd;
    }

    public void tokenClear() {
        O_AUTH_RESPONSE = null;
    }
}
