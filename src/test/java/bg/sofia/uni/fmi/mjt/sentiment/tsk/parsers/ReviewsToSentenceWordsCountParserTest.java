package bg.sofia.uni.fmi.mjt.sentiment.tsk.parsers;

import bg.sofia.uni.fmi.mjt.sentiment.tsk.SentimentAnalyzer;
import bg.sofia.uni.fmi.mjt.sentiment.tsk.review.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewsToSentenceWordsCountParserTest {

    @Mock
    SentimentAnalyzer analyzer;

    @Test
    void testGetSentenceWordsCountOnlySentenceWordsMultipleReviews() {
        Review review1 = new Review(4, "This");
        Review review2 = new Review(2, "This is");
        Review review3 = new Review(3, "This is sentence!");

        List<Review> reviews = List.of(review1, review2, review3);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        Map<String, Long> map = Map.of("this", 3L, "is", 2L, "sentence", 1L);

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected sentence words to be counted correctly");
    }

    @Test
    void testGetSentenceWordsCountDuplicates() {
        Review review1 = new Review(4, "This THIS this is awesome");

        List<Review> reviews = List.of(review1);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        Map<String, Long> map = Map.of("this", 3L, "is", 1L, "awesome", 1L);

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected sentence words to be counted correctly when there are multiple case insensitive");
    }

    @Test
    void testGetSentenceWordsCountDuplicatesMultipleReviews() {
        Review review1 = new Review(4, "This");
        Review review2 = new Review(2, "THIS, this is");
        Review review3 = new Review(3, "This is a sentence!");

        List<Review> reviews = List.of(review1, review2, review3);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        Map<String, Long> map = Map.of("this", 4L, "is", 2L, "sentence", 1L);

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected sentence words to be counted correctly when there are multiple duplicates");
    }

    @Test
    void testGetSentenceWordsCountEmptyReview() {
        Review review1 = new Review(4, "");

        List<Review> reviews = List.of(review1);

        Map<String, Long> map = Map.of();

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected no sentence words to be counted when review is empty");
    }

    @Test
    void testGetSentenceWordsCountBlankReview() {
        Review review1 = new Review(4, "  ");

        List<Review> reviews = List.of(review1);

        Map<String, Long> map = Map.of();

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected no sentence words to be counted when review is blank");
    }

    @Test
    void testGetSentenceWordsCountOneLetterWords() {
        Review review1 = new Review(4, "a - b - c - e f g 1 2 3 . ! ' ");

        List<Review> reviews = List.of(review1);

        Map<String, Long> map = Map.of();

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected no sentence words to be counted when review has no sentence words");
    }

    @Test
    void testGetSentenceWordsCountStopWordsOnly() {
        Review review1 = new Review(4, "stopwords only");

        when(analyzer.isStopWord(any(String.class))).thenReturn(true);

        List<Review> reviews = List.of(review1);

        Map<String, Long> map = Map.of();

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected no sentence words to be counted when review has only stopwords");
    }

    @Test
    void testGetSentenceWordsCountOneReviewWithStopWords() {
        Review review1 = new Review(4, " good stopwords stopwords2 stopwords");

        when(analyzer.isStopWord("good")).thenReturn(false);
        when(analyzer.isStopWord("stopwords")).thenReturn(true);
        when(analyzer.isStopWord("stopwords2")).thenReturn(true);

        List<Review> reviews = List.of(review1);

        Map<String, Long> map = Map.of("good", 1L);

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected no sentence words to be counted when review has multiple stopwords");
    }

    @Test
    void testGetSentenceWordsCountMultipleLinesEmptyReview() {
        Review review1 = new Review(4, "Good");
        Review review2 = new Review(2, "a  v c d ");
        Review review3 = new Review(2, " ");
        Review review4 = new Review(3, "good sentence");

        List<Review> reviews = List.of(review1, review2, review3, review4);

        when(analyzer.isStopWord(any(String.class))).thenReturn(false);

        Map<String, Long> map = Map.of("good", 2L, "sentence", 1L);

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected sentence words to be counted correctly when there are some empty reviews");
    }

    @Test
    void testGetSentenceWordsCountMultipleLinesStopwords() {
        Review review1 = new Review(4, "Good good SentencE");
        Review review2 = new Review(2, "stopword");
        Review review3 = new Review(2, " ");
        Review review4 = new Review(3, "good sentence stopword STOPWORD");

        List<Review> reviews = List.of(review1, review2, review3, review4);

        when(analyzer.isStopWord("good")).thenReturn(false);
        when(analyzer.isStopWord("stopword")).thenReturn(true);
        when(analyzer.isStopWord("sentence")).thenReturn(false);

        Map<String, Long> map = Map.of("good", 3L, "sentence", 2L);

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected sentence words to be counted correctly when there are some empty reviews");
    }

    @Test
    void testGetSentenceWordsCountNoReviews() {
        List<Review> reviews = List.of();

        Map<String, Long> map = Map.of();

        assertEquals(map, ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, analyzer),
            "Expected no sentence words to be counted when there are no reviews");
    }
}
