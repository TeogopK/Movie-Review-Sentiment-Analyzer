package bg.sofia.uni.fmi.mjt.sentiment.tsk.parsers;

import bg.sofia.uni.fmi.mjt.sentiment.tsk.SentimentAnalyzer;
import bg.sofia.uni.fmi.mjt.sentiment.tsk.review.Review;
import bg.sofia.uni.fmi.mjt.sentiment.tsk.words.SentimentWord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewsToSentimentWordsParserTest {
    @Mock
    SentimentAnalyzer analyzer;

    @Test
    void testGetSentimentWordsMultipleLinesNoDuplicates() {
        Review review1 = new Review(4, "This");
        Review review2 = new Review(2, "This is");
        Review review3 = new Review(3, "This is sentence!");

        List<Review> reviews = List.of(review1, review2, review3);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        SentimentWord word1 = new SentimentWord("this", 3, 3);
        SentimentWord word2 = new SentimentWord("is", 2.5, 2);
        SentimentWord word3 = new SentimentWord("sentence", 3, 1);

        Set<SentimentWord> set = Set.of(word1, word2, word3);

        assertEquals(set, ReviewsToSentimentWordsParser.getSentimentWords(reviews, analyzer),
            "Expected sentiment words to be parsed correctly");
    }

    @Test
    void testGetSentimentWordsMultipleLinesWithDuplicates() {
        Review review1 = new Review(4, "This");
        Review review2 = new Review(2, "This this is is");
        Review review3 = new Review(3, "This is IS sentence Sentence!");

        List<Review> reviews = List.of(review1, review2, review3);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        SentimentWord word1 = new SentimentWord("this", 3, 3);
        SentimentWord word2 = new SentimentWord("is", 2.5, 2);
        SentimentWord word3 = new SentimentWord("sentence", 3, 1);

        Set<SentimentWord> set = Set.of(word1, word2, word3);

        assertEquals(set, ReviewsToSentimentWordsParser.getSentimentWords(reviews, analyzer),
            "Expected sentiment words to be parsed correctly when there are duplicates");
    }

    @Test
    void testGetSentimentWordsMultipleLinesStopwords() {
        Review review1 = new Review(4, "This");
        Review review2 = new Review(2, "This this is is");
        Review review3 = new Review(3, "This is IS sentence Sentence!");

        List<Review> reviews = List.of(review1, review2, review3);

        when(analyzer.isStopWord("this")).thenReturn(false);
        when(analyzer.isStopWord("sentence")).thenReturn(false);
        when(analyzer.isStopWord("is")).thenReturn(true);

        SentimentWord word1 = new SentimentWord("this", 3, 3);
        SentimentWord word3 = new SentimentWord("sentence", 3, 1);

        Set<SentimentWord> set = Set.of(word1, word3);

        assertEquals(set, ReviewsToSentimentWordsParser.getSentimentWords(reviews, analyzer),
            "Expected sentiment words to be parsed correctly when there are stopwords");
    }

    @Test
    void testGetSentimentWordsMultipleLinesNoSentimentWords() {
        Review review1 = new Review(4, "   ");
        Review review2 = new Review(2, "This this is is");
        Review review3 = new Review(3, "a f-d-s 1 2 3 is, IS ,,, this!");

        List<Review> reviews = List.of(review1, review2, review3);

        when(analyzer.isStopWord("this")).thenReturn(true);
        when(analyzer.isStopWord("is")).thenReturn(true);

        Set<SentimentWord> set = Set.of();

        assertEquals(set, ReviewsToSentimentWordsParser.getSentimentWords(reviews, analyzer),
            "Expected no sentiment words to be parsed when there are no sentiment words");
    }

    @Test
    void testGetSentimentWordsOneReview() {
        Review review1 = new Review(4, "This is a sentence!");

        List<Review> reviews = List.of(review1);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        SentimentWord word1 = new SentimentWord("this", 4, 1);
        SentimentWord word2 = new SentimentWord("is", 4, 1);
        SentimentWord word3 = new SentimentWord("sentence", 4, 1);

        Set<SentimentWord> set = Set.of(word1, word2, word3);

        assertEquals(set, ReviewsToSentimentWordsParser.getSentimentWords(reviews, analyzer),
            "Expected sentiment words to be parsed correctly from one review");
    }

    @Test
    void testGetSentimentWordsNoReviews() {
        List<Review> reviews = List.of();

        Set<SentimentWord> set = Set.of();

        assertEquals(set, ReviewsToSentimentWordsParser.getSentimentWords(reviews, analyzer),
            "Expected no sentiment words to be parsed when there are no reviews");
    }

    @Test
    void testGetSentimentWordsTwoSameReviews() {
        Review review1 = new Review(4, "This is a sentence!");
        Review review2 = new Review(4, "This is a sentence!");

        List<Review> reviews = List.of(review1, review2);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        SentimentWord word1 = new SentimentWord("this", 4, 2);
        SentimentWord word2 = new SentimentWord("is", 4, 2);
        SentimentWord word3 = new SentimentWord("sentence", 4, 2);

        Set<SentimentWord> set = Set.of(word1, word2, word3);

        assertEquals(set, ReviewsToSentimentWordsParser.getSentimentWords(reviews, analyzer),
            "Expected sentiment words to be parsed correctly when there are two same reviews");
    }

    @Test
    void testGetSentimentWordsCaseInsensitive() {
        Review review1 = new Review(4, "This is a sentence!");
        Review review2 = new Review(4, "THIS IS A SENTENCE!");

        List<Review> reviews = List.of(review1, review2);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        SentimentWord word1 = new SentimentWord("this", 4, 2);
        SentimentWord word2 = new SentimentWord("is", 4, 2);
        SentimentWord word3 = new SentimentWord("sentence", 4, 2);

        Set<SentimentWord> set = Set.of(word1, word2, word3);

        assertEquals(set, ReviewsToSentimentWordsParser.getSentimentWords(reviews, analyzer),
            "Expected sentiment words to be parsed correctly when there are case insensitive reviews");
    }

    @Test
    void testGetSentimentWordsTwoSameReviewsDifferentScores() {
        Review review1 = new Review(4, "This is a sentence!");
        Review review2 = new Review(0, "This is a sentence!");

        List<Review> reviews = List.of(review1, review2);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        SentimentWord word1 = new SentimentWord("this", 2, 2);
        SentimentWord word2 = new SentimentWord("is", 2, 2);
        SentimentWord word3 = new SentimentWord("sentence", 2, 2);

        Set<SentimentWord> set = Set.of(word1, word2, word3);

        assertEquals(set, ReviewsToSentimentWordsParser.getSentimentWords(reviews, analyzer),
            "Expected sentiment words to be parsed correctly when there are two same reviews");
    }

    @Test
    void testGetSentimentWordsDoubleRatings() {
        Review review1 = new Review(4, "This is a sentence!");
        Review review2 = new Review(0, "This is sentence!");
        Review review3 = new Review(0, "This a sentence!");
        Review review4 = new Review(1, "is Sentence");

        List<Review> reviews = List.of(review1, review2, review3, review4);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        SentimentWord word1 = new SentimentWord("this", (double) 4 / 3, 3);
        SentimentWord word2 = new SentimentWord("is", (double) 5 / 3, 3);
        SentimentWord word3 = new SentimentWord("sentence", 1.25, 4);

        Set<SentimentWord> set = Set.of(word1, word2, word3);

        assertEquals(set, ReviewsToSentimentWordsParser.getSentimentWords(reviews, analyzer),
            "Expected sentiment words to be parsed correctly when there are double numbers");
    }
}
