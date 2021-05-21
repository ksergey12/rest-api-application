package com.rest.api.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.model.FioRequest;
import com.rest.api.model.view.FioResponse;
import com.rest.api.util.AesEncryptor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Log4j
public class FioService {

    @Autowired
    private AesEncryptor aesEncryptor;

    public FioResponse getById(FioRequest fioRequest) throws JsonProcessingException {
        FioResponse fioResponse = new FioResponse();

        if (fioRequest.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required parameter: id");
        } else if (fioRequest.getId() == 1) {
            fioResponse.setFio("Test Testov");
        }

        String jsonFioRequest = getJson(fioRequest);
        String jsonFioResponse = getJson(fioResponse);

        log.info("REQUEST");
        String encryptedRequest = aesEncryptor.encrypt(jsonFioRequest);
        String decryptedRequest = aesEncryptor.decrypt(encryptedRequest);
        log.info("=== encryption: " + encryptedRequest);
        log.info("=== decryption: " + decryptedRequest);

        log.info("RESPONSE");
        String encryptedResponse = aesEncryptor.encrypt(jsonFioResponse);
        String decryptedResponse = aesEncryptor.decrypt(encryptedResponse);
        log.info("=== encryption: " + encryptedResponse);
        log.info("=== decryption: " + decryptedResponse);
        log.info("----------------------------------\n");

        return fioResponse;
    }

    private String getJson(Object input) throws JsonProcessingException {
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writeValueAsString(input);
    }
}
