package bg.sofia.uni.fmi.mjt.sentiment.tsk.parsers.file;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class FileToStopwordsParserTest {

    @Test
    void testGetStopwordsOneLine() {
        String input = "word" + System.lineSeparator();
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Set<String> set = Set.of("word");

        assertEquals(set, FileToStopwordsParser.getStopwords(reader),
            "Expected stopwords to be parsed correctly when there is only one stopword");
    }

    @Test
    void testGetStopwordsOneLineNoLineSeparator() {
        String input = "word";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Set<String> set = Set.of("word");

        assertIterableEquals(set, FileToStopwordsParser.getStopwords(reader),
            "Expected stopwords to be parsed correctly when there is one stopword without line separators");
    }

    @Test
    void testGetStopwordsBlank() {
        String input = "  ";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertEquals(Collections.emptySet(), FileToStopwordsParser.getStopwords(reader),
            "Expected no stopwords to be parsed when input is blank");
    }

    @Test
    void testGetStopwordsEmpty() {
        String input = "";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertEquals(Collections.emptySet(), FileToStopwordsParser.getStopwords(reader),
            "Expected no stopwords to be parsed when input is empty");
    }

    @Test
    void testGetStopwordsOneLineWithSpaces() {
        String input = "   word   " + System.lineSeparator();
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Set<String> set = Set.of("word");

        assertEquals(set, FileToStopwordsParser.getStopwords(reader),
            "Expected stopwords to be parsed correctly when there is one stopword with trimming spaces");
    }

    @Test
    void testGetStopwordsOneLetterWord() {
        String input = "i" + System.lineSeparator();
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Set<String> set = Set.of("i");

        assertEquals(set, FileToStopwordsParser.getStopwords(reader),
            "Expected stopword to be parsed correctly when it has one letter");
    }

    @Test
    void testGetStopwordsDuplicates() {
        String input = "word" + System.lineSeparator() + "word";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Set<String> set = Set.of("word");

        assertEquals(set, FileToStopwordsParser.getStopwords(reader),
            "Expected stopwords to be parsed correctly when there are two equal stopwords");

        assertEquals(1, set.size(), "Expected two equal stopwords to be parsed as one");
    }

    @Test
    void testGetStopwordsDuplicatesCaseInsensitive() {
        String input = "word" + System.lineSeparator() + "WORD";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Set<String> set = Set.of("word");

        assertEquals(set, FileToStopwordsParser.getStopwords(reader),
            "Expected stopwords to be parsed correctly when are two equal stopwords case insensitive");

        assertEquals(1, set.size(), "Expected two equal stopwords to be parsed as one");
    }

    @Test
    void testGetStopwordsMultipleWords() {
        String input =
            System.lineSeparator() + "first" + System.lineSeparator() + "second" + System.lineSeparator() + "third" +
                System.lineSeparator();

        BufferedReader reader = new BufferedReader(new StringReader(input));

        Set<String> set = Set.of("first", "second", "third");

        assertEquals(set, FileToStopwordsParser.getStopwords(reader), "Expected stopwords to be parsed correctly");

        assertEquals(3, set.size(), "Expected all stopwords to be parsed");
    }

    @Test
    void testGetStopwordsMultipleWordsWithSymbols() {
        String input =
            "#first" + System.lineSeparator() + "@second" + System.lineSeparator() + "th^ird" + System.lineSeparator();

        BufferedReader reader = new BufferedReader(new StringReader(input));

        Set<String> set = Set.of("#first", "@second", "th^ird");

        assertEquals(set, FileToStopwordsParser.getStopwords(reader), "Expected stopwords to be parsed correctly");

        assertEquals(3, set.size(), "Expected all stopwords to be parsed");
    }

    @Test
    void testGetStopwordsMultipleWordsWithApostrophe() {
        String input =
            System.lineSeparator() + "doesn't" + System.lineSeparator() + "can't" + System.lineSeparator() + "you're";

        BufferedReader reader = new BufferedReader(new StringReader(input));

        Set<String> set = Set.of("doesn't", "can't", "you're");

        assertEquals(set, FileToStopwordsParser.getStopwords(reader), "Expected stopwords to be parsed correctly");

        assertEquals(3, set.size(), "Expected all stopwords to be parsed");
    }
}

