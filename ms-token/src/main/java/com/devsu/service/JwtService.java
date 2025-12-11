package com.devsu.service;

import com.devsu.model.JwtProperties;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class JwtService {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final long expirationMillis;
    private final String issuer;
    private final List<String> audience;

    public JwtService(JwtKeyProvider keyProvider, JwtProperties props) {
        this.privateKey = keyProvider.privateKey();
        this.publicKey = keyProvider.publicKey();
        this.expirationMillis = props.getExpiration();
        this.issuer = props.getIssuer();
        this.audience = props.getAudience();
    }

    public String generateToken(String subject, String scope, String aud) throws Exception {
        Instant now = Instant.now();
        Instant exp = now.plusMillis(expirationMillis);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(issuer)
                .audience(aud)
                .issueTime(Date.from(now))
                .expirationTime(Date.from(exp))
                .claim("scope", scope)
                .build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claims);
        JWSSigner signer = new RSASSASigner(privateKey);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public JWTClaimsSet validateAndGetClaims(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);

        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("Firma inválida");
        }

        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        Date now = new Date();

        if (claims.getExpirationTime() == null || now.after(claims.getExpirationTime())) {
            throw new JOSEException("Token expirado");
        }
        if (!issuer.equals(claims.getIssuer())) {
            throw new JOSEException("Issuer inválido");
        }
        if (audience.stream().noneMatch(claims.getAudience()::contains)) {
            throw new JOSEException("Audience inválida");
        }
        return claims;
    }
}

