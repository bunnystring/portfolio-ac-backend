package com.backend.portfolio_ac.service.impl;

import com.backend.portfolio_ac.service.OAuth2TokenService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OAuth2TokenSerivceImpl implements OAuth2TokenService {

    @Value("${azure.oauth2.client-id}")
    private String clientId;

    @Value("${azure.oauth2.client-secret}")
    private String clientSecret;

    @Value("${azure.oauth2.tenant-id}")
    private String tenantId;

    @Value("${azure.oauth2.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getAccessToken() {
        String tokenUrl = "https://login.microsoftonline.com/" + tenantId + "/oauth2/v2.0/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> params = new LinkedHashMap<>();
        params.put("client_id", clientId);
        params.put("scope", "https://graph.microsoft.com/.default");
        params.put("grant_type", "client_credentials");
        params.put("client_secret", clientSecret);

        StringBuilder bodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            bodyBuilder.append(entry.getKey()).append("=")
                    .append(entry.getValue()).append("&");
        }
        String body = bodyBuilder.substring(0, bodyBuilder.length() - 1);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                tokenUrl, HttpMethod.POST, entity, Map.class);

        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null || !responseBody.containsKey("access_token")) {
            throw new RuntimeException("No se pudo obtener el access token");
        }
        return (String) responseBody.get("access_token");
    }
}
