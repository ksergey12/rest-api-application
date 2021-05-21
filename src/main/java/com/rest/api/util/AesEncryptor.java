package com.rest.api.util;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

@Component
public class AesEncryptor implements Encryptor{

    final String secretKey = "super_secret_key";
    final String salt = KeyGenerators.string().generateKey();
    private final TextEncryptor encryptor;

    public AesEncryptor() {
        encryptor = Encryptors.text(secretKey, salt);
    }

    @Override
    public String encrypt(String body) {
        return encryptor.encrypt(body);
    }

    @Override
    public String decrypt(String body) {
        return encryptor.decrypt(body);
    }
}
