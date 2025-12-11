package com.devsu.karate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtUtil {
    public static String createToken() {
        Algorithm algorithm = Algorithm.HMAC256("secret123");
        return JWT.create()
                .withSubject("test-user")
                .withClaim("role", "ADMIN")
                .sign(algorithm);
    }
}
