package com.devsu.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "jwt")
@Component
@Getter
@Setter
public class JwtProperties {
    private String issuer;
    private List<String> audience;
    private long expiration;
}
