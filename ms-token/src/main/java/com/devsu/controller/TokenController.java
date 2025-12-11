package com.devsu.controller;

import com.devsu.service.JwtService;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Value("${jwt.secret}")
    private String clientSecret;

    private final JwtService jwtService;

    public TokenController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> issueToken(@RequestHeader(value = "x-subject") String subject, @RequestHeader(value = "x-scope") String scope, @RequestHeader(value = "x-client-secret") String clientProvider, @RequestHeader(value = "x-aud") String aud) throws Exception {
        if (!validateSecret(clientProvider, clientSecret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid customer"));
        }
        String token = jwtService.generateToken(subject, scope, aud);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("token");
            jwtService.validateAndGetClaims(token);
            return ResponseEntity.ok(Map.of(
                    "valid", "true"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                    "valid", "false",
                    "error", e.getMessage()
            ));
        }
    }

    private boolean validateSecret(String providedSecretBase64, String expectedSecret) {
        byte[] providedBytes = Base64.getDecoder().decode(providedSecretBase64);
        byte[] expectedBytes = Base64.getDecoder().decode(expectedSecret);
        return MessageDigest.isEqual(providedBytes, expectedBytes);
    }

}