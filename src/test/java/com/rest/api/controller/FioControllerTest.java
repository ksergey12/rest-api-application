package com.rest.api.controller;

import com.rest.api.service.FioService;
import com.rest.api.util.AesEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {FioController.class, FioService.class, AesEncryptor.class})
class FioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        mockMvc.perform(post("/fio")
                .content("{\"id\": \"1\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenValidInput_thenReturnsValidJson() throws Exception {
        mockMvc.perform(post("/fio")
                .content("{\"id\": \"1\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fio").value("Test Testov"));
    }

    @Test
    void whenInvalidInput_thenReturns400() throws Exception {
        mockMvc.perform(post("/fio")
                .content("{\"id\": \"0\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenInputWithoutParams_thenReturns400() throws Exception {
        mockMvc.perform(post("/fio")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenWrongQueryMethod_thenReturns405() throws Exception {
        mockMvc.perform(get("/fio")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }
}