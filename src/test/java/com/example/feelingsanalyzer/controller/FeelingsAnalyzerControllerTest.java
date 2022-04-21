package com.example.feelingsanalyzer.controller;

import com.example.feelingsanalyzer.exception.BadRequestException;
import com.example.feelingsanalyzer.model.FeelingsAnalyzerRequest;
import com.example.feelingsanalyzer.service.FeelingsAnalyzerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FeelingsAnalyzerControllerTest {

    private static final String URI = "/feelings-analyzer/analyze";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeelingsAnalyzerService feelingsAnalyzerService;

    @Test
    public void testAnalyze() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        FeelingsAnalyzerRequest request = new FeelingsAnalyzerRequest();
        request.setText("test");
        String requestJson = objectMapper.writeValueAsString(request);
        when(feelingsAnalyzerService.analyze(any())).thenReturn(any());
        mockMvc.perform(post(URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk());
    }

    @Test
    public void testAnalyzeBadRequestException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        FeelingsAnalyzerRequest request = new FeelingsAnalyzerRequest();
        request.setText("test");
        String requestJson = objectMapper.writeValueAsString(request);
        when(feelingsAnalyzerService.analyze(any())).thenThrow(BadRequestException.class);
        mockMvc.perform(post(URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isBadRequest());
    }
}