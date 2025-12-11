package com.devsu.connector;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenClient {

    @Value("${api.jwt}")
    private String urlToken;

    @Value("${SECRET_PUBLIC_KEY}")
    private String publicKeyBase64;

    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    public String getToken() {
        String key = "jwt:service";
        String token = redisTemplate.opsForValue().get(key);

        if (token != null) {
            if (!isExpired(token)) {
                return token;
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-subject", "public-client");
        headers.set("x-scope", "read:customers");
        headers.set("x-client-secret", "elBFNzNhWHVrWWJrZThqSTloMzUzUzNabUc2dlQwcVArbXNLNTU0QWkrTT0=");
        headers.set("x-aud", "ms-users");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(urlToken, HttpMethod.GET, entity, Map.class);

        Map<String, Object> body = response.getBody();
        token = (String) body.get("token");

        redisTemplate.opsForValue().set(key, token, Duration.ofMinutes(5));

        return token;
    }

    private boolean isExpired(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(loadPublicKey(publicKeyBase64))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration().before(new Date());
    }

    private PublicKey loadPublicKey(String base64Key) {
        try {
            byte[] decoded = Base64.getDecoder().decode(base64Key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (Exception e) {
            return null;
        }
    }
}
