package bg.sofia.uni.fmi.mjt.sentiment.parsers.review;

import bg.sofia.uni.fmi.mjt.sentiment.SentimentAnalyzer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewTextToSentenceWordsParserTest {
    @Mock
    SentimentAnalyzer analyzer;

    @Test
    void testGetSentenceWordsNoStopWords() {
        String input = "This is sentence!";

        List<String> list = List.of("this", "is", "sentence");

        when(analyzer.isStopWord("this")).thenReturn(false);
        when(analyzer.isStopWord("is")).thenReturn(false);
        when(analyzer.isStopWord("sentence")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected sentence words to be parsed correctly");
    }

    @Test
    void testGetSentenceWordsTwoOfThreeStopwords() {
        String input = "This was good!";

        List<String> list = List.of("good");

        when(analyzer.isStopWord("this")).thenReturn(true);
        when(analyzer.isStopWord("was")).thenReturn(true);
        when(analyzer.isStopWord("good")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected stopwords to be ignored");
    }

    @Test
    void testGetSentenceWordsOnlyStopwords() {
        String input = "Have him";

        List<String> list = List.of();

        when(analyzer.isStopWord("have")).thenReturn(true);
        when(analyzer.isStopWord("him")).thenReturn(true);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected empty list when the input consists of only stopwords");
    }

    @Test
    void testGetSentenceWordsIgnoreSymbols() {
        String input = "!@#$ 1st   doesn't arrive!  ";

        List<String> list = List.of("1st", "doesn't", "arrive");

        when(analyzer.isStopWord("1st")).thenReturn(false);
        when(analyzer.isStopWord("doesn't")).thenReturn(false);
        when(analyzer.isStopWord("arrive")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected sentence words to be parsed correctly");
    }

    @Test
    void testGetSentenceWordsApostrophe() {
        String input = "I'm here";

        List<String> list = List.of("i'm", "here");

        when(analyzer.isStopWord("i'm")).thenReturn(false);
        when(analyzer.isStopWord("here")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected sentence words to be parsed correctly when word has apostrophe");
    }

    @Test
    void testGetSentenceWordsTwoApostrophes() {
        String input = "'' here";

        List<String> list = List.of("''", "here");

        when(analyzer.isStopWord("''")).thenReturn(false);
        when(analyzer.isStopWord("here")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected sentence words to be parsed correctly when word is two apostrophes");
    }

    @Test
    void testGetSentenceWordsTwoSize() {
        String input = "'' here";

        List<String> list = List.of("''", "here");

        when(analyzer.isStopWord("''")).thenReturn(false);
        when(analyzer.isStopWord("here")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected sentence words to be parsed correctly when word is two apostrophes");
    }

    @Test
    void testGetSentenceWordsOneApostrophe() {
        String input = "'";

        List<String> list = List.of();

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected empty list when the input consists of only one apostrophe");
    }

    @Test
    void testGetSentenceWordsOneLetter() {
        String input = "q";

        List<String> list = List.of();

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected empty list when the input consists of only one letter");
    }

    @Test
    void testGetSentenceWordsOneDigit() {
        String input = "1";

        List<String> list = List.of();

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected empty list when the input consists of only one letter");
    }

    @Test
    void testGetSentenceWordsTwoDigitNumber() {
        String input = "12";

        List<String> list = List.of("12");

        when(analyzer.isStopWord("12")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected number with at least two digits to be parsed correctly as sentence word");
    }

    @Test
    void testGetSentenceWordsWordWithNumber() {
        String input = "100qn";

        List<String> list = List.of("100qn");

        when(analyzer.isStopWord("100qn")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected word with number in between to be parsed correctly as sentence word");
    }

    @Test
    void testGetSentenceWordsMultipleWordsWithNumbers() {
        String input = "n1mbers 1000 4our letter5";

        List<String> list = List.of("n1mbers", "1000", "4our", "letter5");

        when(analyzer.isStopWord("n1mbers")).thenReturn(false);
        when(analyzer.isStopWord("1000")).thenReturn(false);
        when(analyzer.isStopWord("4our")).thenReturn(false);
        when(analyzer.isStopWord("letter5")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected words with numbers to be parsed correctly as sentence words");
    }

    @Test
    void testGetSentenceWordsWordWithHyphen() {
        String input = "co-operation";

        List<String> list = List.of("co", "operation");

        when(analyzer.isStopWord("co")).thenReturn(false);
        when(analyzer.isStopWord("operation")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected word with hyphen to be parsed correctly as two words");
    }

    @Test
    void testGetSentenceWordsWordWithHyphenAndNumber() {
        String input = "100-years";

        List<String> list = List.of("100", "years");

        when(analyzer.isStopWord("100")).thenReturn(false);
        when(analyzer.isStopWord("years")).thenReturn(false);

        assertIterableEquals(list, ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer),
            "Expected word with hyphen to be parsed correctly as two words");
    }

    @Test
    void testGetSentenceWordsDuplicated() {
        String input = "word WORD";
        List<String> list = List.of("word", "word");

        when(analyzer.isStopWord("word")).thenReturn(false);

        List<String> actual = ReviewTextToSentenceWordsParser.getSentenceWords(input, analyzer);

        assertIterableEquals(list, actual, "Expected duplicate words to be parsed correctly as two words");
        assertEquals(2, actual.size(), "Expected duplicate words to be parsed correctly as two words");
    }
}
