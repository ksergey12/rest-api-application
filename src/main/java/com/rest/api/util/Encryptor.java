package com.rest.api.util;

public interface Encryptor {

    String encrypt(String body);

    String decrypt(String body);
}
