package com.devsu.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class JwtKeyProvider {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public JwtKeyProvider(@Value("${jwt.private-key}") String privateKeyBase64, @Value("${jwt.public-key}") String publicKeyBase64) throws Exception {

        byte[] privateBytes = Base64.getDecoder().decode(privateKeyBase64);
        this.privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateBytes));

        byte[] publicBytes = Base64.getDecoder().decode(publicKeyBase64);
        this.publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicBytes));
    }

    public PrivateKey privateKey() { return privateKey; }
    public PublicKey publicKey() { return publicKey; }

}

