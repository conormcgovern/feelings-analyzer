package com.example.feelingsanalyzer.service;

import com.example.feelingsanalyzer.exception.BadRequestException;
import com.example.feelingsanalyzer.model.FeelingsAnalysis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.feelingsanalyzer.service.FeelingsAnalyzerService.PHRASE_NOT_FOUND_MESSAGE;
import static com.example.feelingsanalyzer.service.FeelingsAnalyzerService.TEXT_IS_REQUIRED_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FeelingsAnalyzerServiceTest {

    private FeelingsAnalyzerService service;

    @BeforeEach
    public void setup() {
        service = new FeelingsAnalyzerService();
    }

    @Test
    public void testAnalyze() {
        String text = "I am Happy! I had a good day? Now I am sad.";
        FeelingsAnalysis analysis = service.analyze(text);
        assertEquals(1, analysis.getGood());
        assertEquals(1, analysis.getBad());
    }

    @Test
    public void testAnalyzeWhenWordNotInCategory() {
        String text = "Do you think i am funny?";
        FeelingsAnalysis expected = new FeelingsAnalysis();
        FeelingsAnalysis analysis = service.analyze(text);
        assertEquals(0, analysis.getGood());
        assertEquals(0, analysis.getBad());
    }

    @Test
    public void testAnalyzeWhenPhraseNotFound() {
        String text = "Geri am hungry!";
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> service.analyze(text));
        assertEquals(PHRASE_NOT_FOUND_MESSAGE, thrown.getMessage());
    }

    @Test
    public void testAnalyzeWhenNoText() {
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> service.analyze(null));
        assertEquals(TEXT_IS_REQUIRED_MESSAGE, thrown.getMessage());
    }

    @Test
    public void testAnalyzeWhenNoPunctuation() {
        String text = "I am blah";
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> service.analyze(text));
        assertEquals(PHRASE_NOT_FOUND_MESSAGE, thrown.getMessage());
    }

    @Test
    public void testFindPotentialFeelingWordsWhenOneMatch() {
        List<String> feelings = service.findPotentialFeelingWords("Good morning, I am happy.");
        assertEquals(1, feelings.size());
        assertEquals("happy", feelings.get(0));
    }

    @Test
    public void testFindPotentialFeelingWordsWhenMultipleMatches() {
        List<String> feelings = service.findPotentialFeelingWords("I am happy. I am cool.");
        assertEquals(2, feelings.size());
        assertEquals("happy", feelings.get(0));
        assertEquals("cool", feelings.get(1));
    }


    @Test
    public void testFindPotentialFeelingWordsWhenKeywordIsLowercase() {
        List<String> feelings = service.findPotentialFeelingWords("Good morning, i am happy!");
        assertEquals(1, feelings.size());
        assertEquals("happy", feelings.get(0));
    }

    @Test
    public void testFindPotentialFeelingWordsWhenKeywordIsUppercase() {
        List<String> feelings = service.findPotentialFeelingWords("Good morning, I AM happy!");
        assertEquals(1, feelings.size());
        assertEquals("happy", feelings.get(0));
    }

    @Test
    public void testFindPotentialFeelingWordsWhenMatchIsUppercase() {
        List<String> feelings = service.findPotentialFeelingWords("Good morning, I am HAPPY!");
        assertEquals(1, feelings.size());
        assertEquals("happy", feelings.get(0));
    }

    @Test
    public void testFindPotentialFeelingWordsWhenNoTrailingPunctuation() {
        List<String> feelings = service.findPotentialFeelingWords("I am happy how are you?");
        assertEquals(0, feelings.size());
    }

}