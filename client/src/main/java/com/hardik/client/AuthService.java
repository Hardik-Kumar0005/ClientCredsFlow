package com.hardik.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Value("${resourceServerUri}")
    String resourceServerUri;

    private final RestTemplate restTemplate;
    private final OAuth2AuthorizedClientManager manager;

    public AuthService(RestTemplate restTemplate, OAuth2AuthorizedClientManager manager) {
        this.restTemplate = restTemplate;
        this.manager = manager;
    }

    public String fetchData(){
        var authRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak-client")
                .principal("self")
                .build();

        var client = manager.authorize(authRequest);
        String token = client.getAccessToken().getTokenValue();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        var rest = restTemplate.exchange(
                resourceServerUri + "/resource",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class);

        return rest.getBody();
    }
}
