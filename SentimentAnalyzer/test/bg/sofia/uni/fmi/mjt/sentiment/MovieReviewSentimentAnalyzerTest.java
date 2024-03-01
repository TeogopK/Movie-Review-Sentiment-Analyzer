package bg.sofia.uni.fmi.mjt.sentiment;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieReviewSentimentAnalyzerTest {

    private MovieReviewSentimentAnalyzer analyzer =
        new MovieReviewSentimentAnalyzer(new StringReader("text"), new StringReader("text"), new StringWriter(100));

    @Test
    void testGetMostFrequentWordsNisNegative() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.getMostFrequentWords(-1),
            "IllegalArgumentException expected to be thrown when N is negative");
    }

    @Test
    void testGetMostPositiveWordsNisNegative() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.getMostPositiveWords(-1),
            "IllegalArgumentException expected to be thrown when N is negative");
    }

    @Test
    void testGetMostNegativeWordsNisNegative() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.getMostNegativeWords(-1),
            "IllegalArgumentException expected to be thrown when N is negative");
    }

    @Test
    void testAppendReviewReviewIsNull() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.appendReview(null, 1),
            "IllegalArgumentException expected to be thrown when review is null");
    }

    @Test
    void testAppendReviewReviewIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.appendReview("", 1),
            "IllegalArgumentException expected to be thrown when review is empty");
    }

    @Test
    void testAppendReviewReviewIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.appendReview(" ", 1),
            "IllegalArgumentException expected to be thrown when review is blank");
    }

    @Test
    void testAppendReviewSentimentIsFive() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.appendReview("review", 5),
            "IllegalArgumentException expected to be thrown when sentiment is 5");
    }

    @Test
    void testAppendReviewSentimentIsEleven() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.appendReview("review", 11),
            "IllegalArgumentException expected to be thrown when sentiment is 11");
    }

    @Test
    void testAppendReviewSentimentIsMinusOne() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.appendReview("review", -1),
            "IllegalArgumentException expected to be thrown when sentiment is -1");
    }

    @Test
    void testAppendReviewSentimentIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.appendReview("review", -5),
            "IllegalArgumentException expected to be thrown when sentiment is -5");
    }

    @Test
    void testAppendReviewSentimentIsZero() {
        assertDoesNotThrow(() -> analyzer.appendReview("review", 0),
            "Exception was not expected to be thrown when sentiment is 0");
    }

    @Test
    void testAppendReviewSentimentIsFour() {
        assertDoesNotThrow(() -> analyzer.appendReview("review", 4),
            "Exception was not expected to be thrown when sentiment is 4");
    }

    @Test
    void testAppendReviewSentimentIsTwo() {
        assertDoesNotThrow(() -> analyzer.appendReview("review", 2),
            "Exception was not expected to be thrown when sentiment is 2");
    }

    @Test
    void testAppendReviewReviewHasLineSeparator() {
        assertThrows(IllegalArgumentException.class,
            () -> analyzer.appendReview("abc" + System.lineSeparator() + "def", 1),
            "IllegalArgumentException expected to be thrown when review is has line separator");
    }

    void setAnalyzer() {
        String reviews = "3 Good film" + System.lineSeparator() + "1 a bad film!" + System.lineSeparator() +
            "2 BAD GOOD neutral film";

        String stopwords = "a" + System.lineSeparator() + "this" + System.lineSeparator();

        analyzer = new MovieReviewSentimentAnalyzer(new StringReader(stopwords), new StringReader(reviews),
            new StringWriter());
    }

    @Test
    void testGetSentimentDictionarySize() {
        setAnalyzer();

        assertEquals(4, analyzer.getSentimentDictionarySize(), "Expected dictionary to be parsed correctly");
    }

    @Test
    void testGetSentimentDictionarySizeEmpty() {
        analyzer = new MovieReviewSentimentAnalyzer(new StringReader(""), new StringReader(""), new StringWriter());

        assertEquals(0, analyzer.getSentimentDictionarySize(), "Expected dictionary to be empty when inputs are empty");
    }

    @Test
    void testIsStopWord() {
        setAnalyzer();

        assertTrue(analyzer.isStopWord("a"), "Expected stopword to be viewed as one");
        assertTrue(analyzer.isStopWord("this"), "Expected stopword to be viewed as one");

        assertFalse(analyzer.isStopWord("good"), "Expected sentiment word not to be viewed as stopword");
    }

    @Test
    void testIsStopWordCaseInsensitive() {
        setAnalyzer();

        assertTrue(analyzer.isStopWord("A"), "Expected stopword to be viewed as one");
        assertTrue(analyzer.isStopWord("THis"), "Expected stopword to be viewed as one");
        assertTrue(analyzer.isStopWord("THIS"), "Expected stopword to be viewed as one");
        assertTrue(analyzer.isStopWord("this"), "Expected stopword to be viewed as one");

        assertFalse(analyzer.isStopWord("Good"), "Expected sentiment word not to be viewed as stopword");
    }

    @Test
    void testIsStopWordCaseNotFoundWord() {
        setAnalyzer();

        assertFalse(analyzer.isStopWord("normal"), "Expected random word not to be viewed as stopword");
        assertFalse(analyzer.isStopWord("TEXT''"), "Expected random word not to be viewed as stopword");
        assertFalse(analyzer.isStopWord("!@"), "Expected random word not to be viewed as stopword");
    }

    @Test
    void testGetWordSentiment() {
        setAnalyzer();

        assertEquals(2.5, analyzer.getWordSentiment("good"),
            "Expected sentiment word rating to be calculated correctly");

        assertEquals(1.5, analyzer.getWordSentiment("bad"),
            "Expected sentiment word rating to be calculated correctly");

        assertEquals(2, analyzer.getWordSentiment("neutral"),
            "Expected sentiment word rating to be calculated correctly");
    }

    @Test
    void testGetWordSentimentCaseInsensitive() {
        setAnalyzer();

        assertEquals(2.5, analyzer.getWordSentiment("good"),
            "Expected sentiment word rating to be calculated correctly");

        assertEquals(2.5, analyzer.getWordSentiment("GOOD"),
            "Expected sentiment word rating to be calculated correctly");

        assertEquals(2.5, analyzer.getWordSentiment("gOOd"),
            "Expected sentiment word rating to be calculated correctly");
    }

    @Test
    void testGetWordSentimentStopWord() {
        setAnalyzer();

        assertEquals(-1, analyzer.getWordSentiment("a"), "Expected stopwords rating to be -1");

        assertEquals(-1, analyzer.getWordSentiment("this"), "Expected stopwords rating to be -1");
    }

    @Test
    void testGetWordSentimentRandomWord() {
        setAnalyzer();

        assertEquals(-1, analyzer.getWordSentiment("bulgaria"), "Expected random word rating to be -1");

        assertEquals(-1, analyzer.getWordSentiment("you"), "Expected random word rating to be -1");
    }

    void setAnalyzerBigger() {
        String reviews = "3 Good film" + System.lineSeparator() + "1 a bad film!" + System.lineSeparator() +
            "2 BAD GOOD neutral film" + System.lineSeparator() + "1 bad-worst, bad film ever" + System.lineSeparator() +
            "0 worst terrible" + System.lineSeparator() + "4 fantastic fantastic wh1t wh1t" + System.lineSeparator() +
            "not review" + System.lineSeparator() + "0 wh1t" + System.lineSeparator() + "4 top top top top top top" +
            System.lineSeparator() + "0 this n o t a r e v i e w";

        String stopwords = "a" + System.lineSeparator() + "this" + System.lineSeparator();

        analyzer = new MovieReviewSentimentAnalyzer(new StringReader(stopwords), new StringReader(reviews),
            new StringWriter());
    }

    @Test
    void testGetWordSentimentDouble() {
        setAnalyzerBigger();

        assertEquals(1.333, analyzer.getWordSentiment("bad"), 3,
            "Expected sentiment word rating to be calculated correctly");
    }

    @Test
    void testGetWordSentimentDuplicate() {
        setAnalyzerBigger();

        assertEquals(2, analyzer.getWordSentiment("wh1t"), 3,
            "Expected sentiment word rating to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentDuplicate() {
        setAnalyzerBigger();

        assertEquals(2.25, analyzer.getReviewSentiment("wh1t wh1t fantastic ever!"),
            "Expected sentiment review to be calculated correctly");

        assertEquals(1.333, analyzer.getReviewSentiment("wh1t wh1t terrible"), 3,
            "Expected sentiment review to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentOneMatch() {
        setAnalyzer();

        assertEquals(2.5, analyzer.getReviewSentiment("Good movie!"),
            "Expected sentiment review to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentTwoMatches() {
        setAnalyzer();

        assertEquals(2.25, analyzer.getReviewSentiment("Good film!"),
            "Expected sentiment review to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentThreeMatches() {
        setAnalyzer();

        assertEquals(2, analyzer.getReviewSentiment("Good film, but also bad!"),
            "Expected sentiment review to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentNoMatches() {
        setAnalyzer();

        assertEquals(-1, analyzer.getReviewSentiment("unknown"),
            "Expected sentiment review to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentAsNameUnknown() {
        setAnalyzer();

        assertEquals("unknown", analyzer.getReviewSentimentAsName("Not known"),
            "Expected sentiment review as name to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentAsNameNeutral() {
        setAnalyzer();

        assertEquals("neutral", analyzer.getReviewSentimentAsName("Neutral book"),
            "Expected sentiment review as name to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentAsNameSomewhatPositive() {
        setAnalyzer();

        assertEquals("somewhat positive", analyzer.getReviewSentimentAsName("Good movie!"),
            "Expected sentiment review as name to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentAsNameSomewhatNegative() {
        setAnalyzerBigger();

        assertEquals("somewhat negative", analyzer.getReviewSentimentAsName("worst neutral!"),
            "Expected sentiment review as name to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentAsNameNegative() {
        setAnalyzerBigger();

        assertEquals("negative", analyzer.getReviewSentimentAsName("The most terrible!"),
            "Expected sentiment review as name to be calculated correctly");
    }

    @Test
    void testGetReviewSentimentAsNamePositive() {
        setAnalyzerBigger();

        assertEquals("positive", analyzer.getReviewSentimentAsName("Fantastic job!"),
            "Expected sentiment review as name to be calculated correctly");
    }

    @Test
    void testGetWordFrequency() {
        setAnalyzer();

        assertEquals(2, analyzer.getWordFrequency("good"),
            "Expected sentiment word frequency to be calculated correctly");

        assertEquals(2, analyzer.getWordFrequency("bad"),
            "Expected sentiment word frequency to be calculated correctly");

        assertEquals(1, analyzer.getWordFrequency("neutral"),
            "Expected sentiment word frequency to be calculated correctly");
    }

    @Test
    void testGetWordFrequencyCaseInsensitive() {
        setAnalyzer();

        assertEquals(2, analyzer.getWordFrequency("good"),
            "Expected sentiment word frequency to be calculated correctly");

        assertEquals(2, analyzer.getWordFrequency("GOOD"),
            "Expected sentiment word frequency to be calculated correctly");

        assertEquals(2, analyzer.getWordFrequency("gOOd"),
            "Expected sentiment word frequency to be calculated correctly");
    }

    @Test
    void testGetWordFrequencyStopWord() {
        setAnalyzer();

        assertEquals(0, analyzer.getWordFrequency("a"), "Expected stopwords frequency to be 0");

        assertEquals(0, analyzer.getWordFrequency("this"), "Expected stopwords frequency to be 0");
    }

    @Test
    void testGetWordFrequencyRandomWord() {
        setAnalyzer();

        assertEquals(0, analyzer.getWordFrequency("bulgaria"), "Expected random word frequency to be 0");

        assertEquals(0, analyzer.getWordFrequency("you"), "Expected random word frequency to be 0");
    }

    @Test
    void testGetWordFrequencyLarger() {
        setAnalyzerBigger();

        assertEquals(4, analyzer.getWordFrequency("film"),
            "Expected sentiment word frequency to be calculated correctly");

        assertEquals(4, analyzer.getWordFrequency("bad"),
            "Expected sentiment word frequency to be calculated correctly");

        assertEquals(2, analyzer.getWordFrequency("fantastic"),
            "Expected sentiment word frequency to be calculated correctly");
    }

    @Test
    void testGetWordFrequencyDuplicate() {
        setAnalyzerBigger();

        assertEquals(3, analyzer.getWordFrequency("wh1t"),
            "Expected sentiment word frequency to be calculated correctly");
    }

    @Test
    void testGetMostFrequentWordsOneWord() {
        setAnalyzerBigger();

        List<String> list = List.of("top");

        assertIterableEquals(list, analyzer.getMostFrequentWords(1),
            "Expected most frequent words to be calculated correctly");
    }

    @Test
    void testGetMostFrequentWordsThreeWords() {
        setAnalyzerBigger();

        List<String> list = List.of("top", "bad", "film");

        assertIterableEquals(list, analyzer.getMostFrequentWords(3),
            "Expected most frequent words to be calculated correctly");
    }

    @Test
    void testGetMostFrequentWordsFourWords() {
        setAnalyzerBigger();

        List<String> list = List.of("top", "bad", "film", "wh1t");

        assertIterableEquals(list, analyzer.getMostFrequentWords(4),
            "Expected most frequent words to be calculated correctly");
    }

    @Test
    void testGetMostFrequentWordsZeroWords() {
        setAnalyzerBigger();

        List<String> list = List.of();

        assertIterableEquals(list, analyzer.getMostFrequentWords(0),
            "Expected most frequent words to be calculated correctly");
    }

    @Test
    void testGetMostFrequentBiggerLimit() {
        setAnalyzer();

        List<String> list = List.of("film", "bad", "good", "neutral");

        assertIterableEquals(list, analyzer.getMostFrequentWords(10),
            "Expected most frequent words to be calculated correctly");
    }

    @Test
    void testGetMostFrequentExactLimit() {
        setAnalyzer();

        List<String> list = List.of("film", "bad", "good", "neutral");

        assertIterableEquals(list, analyzer.getMostFrequentWords(4),
            "Expected most frequent words to be calculated correctly");
    }

    @Test
    void testGetMostPositiveWordsOneWord() {
        setAnalyzerBigger();

        List<String> list = List.of("fantastic");

        assertIterableEquals(list, analyzer.getMostPositiveWords(1),
            "Expected most positive words to be calculated correctly");
    }

    @Test
    void testGetMostPositiveWordsThreeWords() {
        setAnalyzerBigger();

        List<String> list = List.of("fantastic", "top", "good");

        assertIterableEquals(list, analyzer.getMostPositiveWords(3),
            "Expected most positive words to be calculated correctly");
    }

    @Test
    void testGetMostPositiveWordsFourWords() {
        setAnalyzerBigger();

        List<String> list = List.of("fantastic", "top", "good", "neutral");

        assertIterableEquals(list, analyzer.getMostPositiveWords(4),
            "Expected most positive words to be calculated correctly");
    }

    @Test
    void testGetMostPositiveWordsZeroWords() {
        setAnalyzerBigger();

        List<String> list = List.of();

        assertIterableEquals(list, analyzer.getMostPositiveWords(0),
            "Expected most positive words to be calculated correctly");
    }

    @Test
    void testGetMostPositiveBiggerLimit() {
        setAnalyzer();

        List<String> list = List.of("good", "film", "neutral", "bad");

        assertIterableEquals(list, analyzer.getMostPositiveWords(10),
            "Expected most positive words to be calculated correctly");
    }

    @Test
    void testGetMostPositiveExactLimit() {
        setAnalyzer();

        List<String> list = List.of("good", "film", "neutral", "bad");

        assertIterableEquals(list, analyzer.getMostPositiveWords(4),
            "Expected most positive words to be calculated correctly");
    }

    @Test
    void testGetMostNegativeWordsOneWord() {
        setAnalyzerBigger();

        List<String> list = List.of("terrible");

        assertIterableEquals(list, analyzer.getMostNegativeWords(1),
            "Expected most negative words to be calculated correctly");
    }

    @Test
    void testGetMostNegativeWordsThreeWords() {
        setAnalyzerBigger();

        List<String> list = List.of("terrible", "worst", "ever");

        assertIterableEquals(list, analyzer.getMostNegativeWords(3),
            "Expected most negative words to be calculated correctly");
    }

    @Test
    void testGetMostNegativeWordsFourWords() {
        setAnalyzerBigger();

        List<String> list = List.of("terrible", "worst", "ever", "bad");

        assertIterableEquals(list, analyzer.getMostNegativeWords(4),
            "Expected most negative words to be calculated correctly");
    }

    @Test
    void testGetMostNegativeWordsZeroWords() {
        setAnalyzerBigger();

        List<String> list = List.of();

        assertIterableEquals(list, analyzer.getMostNegativeWords(0),
            "Expected most negative words to be calculated correctly");
    }

    @Test
    void testGetMostNegativeBiggerLimit() {
        setAnalyzer();

        List<String> list = List.of("bad", "film", "neutral", "good");

        assertIterableEquals(list, analyzer.getMostNegativeWords(10),
            "Expected most negative words to be calculated correctly");
    }

    @Test
    void testGetMostNegativeExactLimit() {
        setAnalyzer();

        List<String> list = List.of("bad", "film", "neutral", "good");

        assertIterableEquals(list, analyzer.getMostNegativeWords(4),
            "Expected most negative words to be calculated correctly");
    }

    @Test
    void testAppendReview() {
        setAnalyzer();

        assertTrue(analyzer.appendReview("New new ninja neutral!", 3),
            "Expected review to be appended successfully");

        assertEquals(1, analyzer.getWordFrequency("ninja"), "Expected new sentiment words to be added correctly");
        assertEquals(2, analyzer.getWordFrequency("new"), "Expected new sentiment words to be added correctly");

        assertEquals(3, analyzer.getWordSentiment("ninja"), "Expected new sentiment words to be added correctly");
        assertEquals(3, analyzer.getWordSentiment("new"), "Expected new sentiment words to be added correctly");

        assertEquals(2, analyzer.getWordFrequency("neutral"), "Expected old sentiment words to be updated correctly");
        assertEquals(2.5, analyzer.getWordSentiment("neutral"), "Expected old sentiment words to be updated correctly");
    }

    @Test
    void testAppendReviewNewWordsFrequency() {
        setAnalyzer();

        assertTrue(analyzer.appendReview("New new ninja neutral!", 3),
            "Expected review to be appended successfully");

        assertEquals(1, analyzer.getWordFrequency("ninja"), "Expected new sentiment words to be added correctly");
        assertEquals(2, analyzer.getWordFrequency("new"), "Expected new sentiment words to be added correctly");
    }

    @Test
    void testAppendReviewOldWordsFrequency() {
        setAnalyzer();

        assertTrue(analyzer.appendReview("New new ninja neutral!", 3),
            "Expected review to be appended successfully");

        assertEquals(2, analyzer.getWordFrequency("neutral"), "Expected old sentiment words to be updated correctly");
    }

    @Test
    void testAppendReviewOldWordsSentiment() {
        setAnalyzer();

        assertTrue(analyzer.appendReview("New new ninja neutral!", 3),
            "Expected review to be appended successfully");

        assertEquals(2.5, analyzer.getWordSentiment("neutral"), "Expected old sentiment words to be updated correctly");
    }

    @Test
    void testAppendReviewNewWordsSentiment() {
        setAnalyzer();

        assertTrue(analyzer.appendReview("New new ninja neutral!", 3),
            "Expected review to be appended successfully");

        assertEquals(3, analyzer.getWordSentiment("ninja"), "Expected new sentiment words to be added correctly");
        assertEquals(3, analyzer.getWordSentiment("new"), "Expected new sentiment words to be added correctly");
    }

    @Test
    void testAppendReviewOldWordsDuplicate() {
        setAnalyzer();

        assertTrue(analyzer.appendReview("New new ninja neutral Neutral NEUTRAL!", 3),
            "Expected review to be appended successfully");

        assertEquals(4, analyzer.getWordFrequency("neutral"), "Expected old sentiment words to be added correctly");
        assertEquals(2.5, analyzer.getWordSentiment("neutral"), "Expected old sentiment words to be added correctly");
    }

    @Test
    void testAppendReviewAppendText() {
        String reviews = "3 Good film" + System.lineSeparator() + "1 a bad film!" + System.lineSeparator() +
            "2 BAD GOOD neutral film";
        String stopwords = "a" + System.lineSeparator() + "this" + System.lineSeparator();

        StringWriter stringWriter = new StringWriter();

        analyzer = new MovieReviewSentimentAnalyzer(new StringReader(stopwords), new StringReader(reviews),
            stringWriter);

        assertTrue(analyzer.appendReview("New ninja approaches!", 4),
            "Expected review to be appended successfully");

        assertEquals("4 New ninja approaches!" + System.lineSeparator(), stringWriter.toString(),
            "Expected new review to be appended correctly");
    }

    @Test
    void testAppendReviewAppendTextMultipleTimes() {
        String reviews = "3 Good film" + System.lineSeparator() + "1 a bad film!" + System.lineSeparator() +
            "2 BAD GOOD neutral film";
        String stopwords = "a" + System.lineSeparator() + "this" + System.lineSeparator();

        StringWriter stringWriter = new StringWriter();

        analyzer = new MovieReviewSentimentAnalyzer(new StringReader(stopwords), new StringReader(reviews),
            stringWriter);

        assertTrue(analyzer.appendReview("New ninja approaches!", 4),
            "Expected review to be appended successfully");

        assertTrue(analyzer.appendReview("A TURTLE?!", 1),
            "Expected review to be appended successfully");

        assertEquals("4 New ninja approaches!" + System.lineSeparator() + "1 A TURTLE?!" + System.lineSeparator(),
            stringWriter.toString(),
            "Expected new review to be appended correctly");
    }
}
