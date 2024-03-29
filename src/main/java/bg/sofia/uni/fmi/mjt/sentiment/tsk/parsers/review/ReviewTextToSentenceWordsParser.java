package bg.sofia.uni.fmi.mjt.sentiment.tsk.parsers.review;

import bg.sofia.uni.fmi.mjt.sentiment.tsk.SentimentAnalyzer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class ReviewTextToSentenceWordsParser {

    private static final String REGEX_ONLY_LATIN_LETTERS_NUMBERS_AND_APOSTROPHE = "[^a-zA-Z0-9']+";

    public static List<String> getSentenceWords(String valueLine, SentimentAnalyzer analyzer) {
        if (valueLine.isEmpty() || valueLine.isBlank()) {
            return Collections.emptyList();
        }

        return Arrays.stream(valueLine.toLowerCase()
                .split(REGEX_ONLY_LATIN_LETTERS_NUMBERS_AND_APOSTROPHE))
            .map(String::strip)
            .filter(s -> s.length() > 1)
            .filter(Predicate.not(analyzer::isStopWord))
            .toList();
    }
}
