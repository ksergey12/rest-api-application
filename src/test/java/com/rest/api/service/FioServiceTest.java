package com.rest.api.service;

import com.rest.api.model.FioRequest;
import com.rest.api.model.view.FioResponse;
import com.rest.api.util.AesEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class FioServiceTest {
    @Mock
    AesEncryptor aesEncryptor;

    @InjectMocks
    FioService fioService;

    @Test
    void whenValidInput_thenReturnsJson() throws Exception {
        FioRequest fioRequest = new FioRequest();
        fioRequest.setId(1);
        when(aesEncryptor.encrypt(anyString())).thenReturn("1234567890");
        when(aesEncryptor.decrypt(anyString())).thenReturn("1234567890");

        FioResponse response = fioService.getById(fioRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Test Testov", response.getFio());
    }

    @Test
    void whenInvalidInput_thenThrowException() {
        FioRequest fioRequest = new FioRequest();
        when(aesEncryptor.encrypt(anyString())).thenReturn("1234567890");
        when(aesEncryptor.decrypt(anyString())).thenReturn("1234567890");

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            fioService.getById(fioRequest);
        });

        String expectedMessage = "Missing required parameter: id";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}