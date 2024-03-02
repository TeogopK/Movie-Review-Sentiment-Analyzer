package bg.sofia.uni.fmi.mjt.sentiment.tsk.parsers.review;

import bg.sofia.uni.fmi.mjt.sentiment.tsk.SentimentAnalyzer;
import bg.sofia.uni.fmi.mjt.sentiment.tsk.review.Review;
import bg.sofia.uni.fmi.mjt.sentiment.tsk.words.MiniWord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewToMiniWordsParserTest {
    @Mock
    SentimentAnalyzer analyzer;

    @Test
    void testGetMiniWords() {
        Review review = new Review(4, "This is sentence!");

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        MiniWord miniWord1 = new MiniWord(4, "this");
        MiniWord miniWord2 = new MiniWord(4, "is");
        MiniWord miniWord3 = new MiniWord(4, "sentence");

        Set<MiniWord> set = Set.of(miniWord1, miniWord2, miniWord3);

        assertEquals(set, ReviewToMiniWordsParser.getMiniWords(review, analyzer),
            "Expected mini words to be parsed correctly");
    }

    @Test
    void testGetMiniWordsDuplicates() {
        Review review = new Review(2, "Good good film!");

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        MiniWord miniWord1 = new MiniWord(2, "good");
        MiniWord miniWord2 = new MiniWord(2, "film");

        Set<MiniWord> set = Set.of(miniWord1, miniWord2);

        assertEquals(set, ReviewToMiniWordsParser.getMiniWords(review, analyzer),
            "Expected mini words to be parsed correctly when there are duplicates");
    }

    @Test
    void testGetMiniWordsTwoDifferentDuplicates() {
        Review review = new Review(2, "Good film, good GOOD film!");

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        MiniWord miniWord1 = new MiniWord(2, "good");
        MiniWord miniWord2 = new MiniWord(2, "film");

        Set<MiniWord> set = Set.of(miniWord1, miniWord2);

        assertEquals(set, ReviewToMiniWordsParser.getMiniWords(review, analyzer),
            "Expected mini words to be parsed correctly when there are multiple duplicates");
    }

    @Test
    void testGetMiniWordDuplicatesWithStopword() {
        Review review = new Review(2, "This Good film, this a good GOOD film!");

        when(analyzer.isStopWord("this")).thenReturn(true);
        when(analyzer.isStopWord("good")).thenReturn(false);
        when(analyzer.isStopWord("film")).thenReturn(false);

        MiniWord miniWord1 = new MiniWord(2, "good");
        MiniWord miniWord2 = new MiniWord(2, "film");

        Set<MiniWord> set = Set.of(miniWord1, miniWord2);

        assertEquals(set, ReviewToMiniWordsParser.getMiniWords(review, analyzer),
            "Expected mini words to be parsed correctly when there are multiple duplicates and a stopword");
    }

    @Test
    void testGetMiniWordDuplicatesWithMultipleStopwords() {
        Review review = new Review(0, "This film is good, very good, doesn't!");

        when(analyzer.isStopWord("this")).thenReturn(true);
        when(analyzer.isStopWord("doesn't")).thenReturn(true);
        when(analyzer.isStopWord("is")).thenReturn(true);
        when(analyzer.isStopWord("very")).thenReturn(true);
        when(analyzer.isStopWord("good")).thenReturn(false);
        when(analyzer.isStopWord("film")).thenReturn(false);

        MiniWord miniWord1 = new MiniWord(0, "good");
        MiniWord miniWord2 = new MiniWord(0, "film");

        Set<MiniWord> set = Set.of(miniWord1, miniWord2);

        assertEquals(set, ReviewToMiniWordsParser.getMiniWords(review, analyzer),
            "Expected mini words to be parsed correctly when there are multiple duplicates and stopwords");
    }

    @Test
    void testGetMiniWordOnlyMiniWords() {
        Review review = new Review(0, "Doesn't MARK01 co-operate with 12! a 1, 2 3 4-5");

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        MiniWord miniWord1 = new MiniWord(0, "doesn't");
        MiniWord miniWord2 = new MiniWord(0, "mark01");
        MiniWord miniWord3 = new MiniWord(0, "co");
        MiniWord miniWord4 = new MiniWord(0, "operate");
        MiniWord miniWord5 = new MiniWord(0, "with");
        MiniWord miniWord6 = new MiniWord(0, "12");

        Set<MiniWord> set = Set.of(miniWord1, miniWord2, miniWord3, miniWord4, miniWord5, miniWord6);

        assertEquals(set, ReviewToMiniWordsParser.getMiniWords(review, analyzer),
            "Expected mini words to be parsed correctly when there are only mini words");
    }

    @Test
    void testGetMiniWordEmptyReview() {
        Review review = new Review(0, "");

        Set<MiniWord> set = Set.of();

        assertEquals(set, ReviewToMiniWordsParser.getMiniWords(review, analyzer),
            "Expected no mini words to be parsed when review text is empty");
    }

    @Test
    void testGetMiniWordBlankReview() {
        Review review = new Review(0, "   ");

        Set<MiniWord> set = Set.of();

        assertEquals(set, ReviewToMiniWordsParser.getMiniWords(review, analyzer),
            "Expected no mini words to be parsed when review text is blank");
    }

    @Test
    void testGetMiniWordOnlyLetters() {
        Review review = new Review(0, "a b c d e f g 1 2 3 4 5 ' - -- + ++ ! !!");

        Set<MiniWord> set = Set.of();

        assertEquals(set, ReviewToMiniWordsParser.getMiniWords(review, analyzer),
            "Expected no mini words to be parsed when review text consists of one letter words");
    }

    @Test
    void testGetMiniWordOnlyStopWords() {
        Review review = new Review(0, "first, second, third");

        Set<MiniWord> set = Set.of();
        when(analyzer.isStopWord(any(String.class))).thenReturn(true);

        assertEquals(set, ReviewToMiniWordsParser.getMiniWords(review, analyzer),
            "Expected no mini words to be parsed when review text consists of only stopwords");
    }

}
