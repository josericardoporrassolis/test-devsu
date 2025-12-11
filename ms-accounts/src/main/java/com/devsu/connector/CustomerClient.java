package com.devsu.connector;

import com.devsu.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerClient {

    @Value("${api.customer}")
    private String urlCustomer;

    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final TokenClient tokenClient;

    public Integer getCustomerByName(String name) {
        String jwt = tokenClient.getToken();

        String key = "customer:name:" + name;
        var idCustomer = redisTemplate.opsForValue().get(key);

        if (idCustomer != null)
            return (Integer) idCustomer;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(urlCustomer + "?name=" + name, HttpMethod.GET, entity, Map.class);

        Map body = response.getBody();

        if(HttpStatus.NO_CONTENT.equals(response.getStatusCode()))
            throw new DataNotFoundException("Cliente no existe");

        Object value = body.get("id");

        redisTemplate.opsForValue().set(key, ((Number) value).longValue(), Duration.ofMinutes(15));

        return (Integer) value;
    }
}