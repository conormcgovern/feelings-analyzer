package com.example.feelingsanalyzer.controller;

import com.example.feelingsanalyzer.model.FeelingsAnalysis;
import com.example.feelingsanalyzer.model.FeelingsAnalyzerRequest;
import com.example.feelingsanalyzer.service.FeelingsAnalyzerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feelings-analyzer")
public class FeelingsAnalyzerController {

    private final FeelingsAnalyzerService feelingsAnalyzerService;

    public FeelingsAnalyzerController(FeelingsAnalyzerService feelingsAnalyzerService) {
        this.feelingsAnalyzerService = feelingsAnalyzerService;
    }

    @PostMapping("/analyze")
    public FeelingsAnalysis analyze(@RequestBody FeelingsAnalyzerRequest request) {
        return feelingsAnalyzerService.analyze(request.getText());
    }
}
