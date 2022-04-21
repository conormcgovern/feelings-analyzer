package com.example.feelingsanalyzer.service;

import com.example.feelingsanalyzer.exception.BadRequestException;
import com.example.feelingsanalyzer.model.FeelingsAnalysis;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FeelingsAnalyzerService {

    protected static final String PHRASE_NOT_FOUND_MESSAGE = "The phrase 'I am X' followed by punctuation was not found.";
    protected static final String TEXT_IS_REQUIRED_MESSAGE = "Text is required.";
    private static final String KEY_WORD = "i am ";
    private static final Pattern PATTERN = Pattern.compile("\\b" + KEY_WORD + "\\w+[.,!?]");
    private static final Set<String> GOOD_WORDS = Set.of("happy", "joy", "love");
    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "hate");

    public FeelingsAnalysis analyze(String text) {
        if (Optional.ofNullable(text).isEmpty()) {
            throw new BadRequestException(TEXT_IS_REQUIRED_MESSAGE);
        }
        List<String> words = findPotentialFeelingWords(text);
        if (words.isEmpty()) {
            throw new BadRequestException(PHRASE_NOT_FOUND_MESSAGE);
        }
        FeelingsAnalysis analysis = initializeFeelingsAnalysis();
        words.forEach(word -> {
            if (GOOD_WORDS.contains(word)) {
                analysis.setGood(Optional.ofNullable(analysis.getGood()).orElse(0) + 1);
            }
            if (BAD_WORDS.contains(word)) {
                analysis.setBad(Optional.ofNullable(analysis.getBad()).orElse(0) + 1);
            }
        });
        return analysis;
    }

    protected List<String> findPotentialFeelingWords(String text) {
        Matcher matcher = PATTERN.matcher(text.toLowerCase());
        return matcher.results().map(matchResult -> extractWordFromMatch(matchResult.group()))
                .collect(Collectors.toList());
    }

    private String extractWordFromMatch(String match) {
        return match.substring(KEY_WORD.length(), match.length() - 1);
    }

    private FeelingsAnalysis initializeFeelingsAnalysis() {
        FeelingsAnalysis analysis = new FeelingsAnalysis();
        analysis.setGood(0);
        analysis.setBad(0);
        return analysis;
    }

}
