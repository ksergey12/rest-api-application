package com.rest.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rest.api.model.FioRequest;
import com.rest.api.model.view.FioResponse;
import com.rest.api.service.FioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "/fio", produces = "application/json")
public class FioController {

    private final FioService fioService;

    public FioController(FioService fioService) {
        Objects.requireNonNull(fioService);
        this.fioService = fioService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> getById(@RequestBody FioRequest fioRequest) {
        FioResponse result;
        try {
            result = fioService.getById(fioRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (result.getFio() != null){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
