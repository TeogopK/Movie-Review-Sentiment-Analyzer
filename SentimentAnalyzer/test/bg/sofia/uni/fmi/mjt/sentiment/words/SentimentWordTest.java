package bg.sofia.uni.fmi.mjt.sentiment.words;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SentimentWordTest {
    private SentimentWord word;

    @Test
    void testAddRatingCurrentAndAddedAreIntegersResultSomewhatPositive() {
        word = new SentimentWord("word", 4, 3);

        word.addRating(0);
        assertEquals(3, word.getRating(), "Expected rating to be calculated correctly when both are integers");
    }

    @Test
    void testAddRatingCurrentAndAddedAreIntegersResultPositive() {
        word = new SentimentWord("word", 4, 3);

        word.addRating(4);
        assertEquals(4, word.getRating(), "Expected rating to be calculated correctly when both are integers");
    }

    @Test
    void testAddRatingCurrentAndAddedAreIntegersResultNeutral() {
        word = new SentimentWord("word", 2, 1);

        word.addRating(2);
        assertEquals(2, word.getRating(), "Expected rating to be calculated correctly when both are integers");
    }

    @Test
    void testAddRatingCurrentAndAddedAreIntegersResultNegative() {
        word = new SentimentWord("word", 0, 10);

        word.addRating(0);
        assertEquals(0, word.getRating(), "Expected rating to be calculated correctly when both are integers");
    }

    @Test
    void testAddRatingCurrentAndAddedAreIntegersResultSomewhatNegative() {
        word = new SentimentWord("word", 0, 2);

        word.addRating(3);
        assertEquals(1, word.getRating(), "Expected rating to be calculated correctly when both are integers");
    }

    @Test
    void testAddRatingTwoTimes() {
        word = new SentimentWord("word", 0, 0);

        word.addRating(3);
        word.addRating(1);

        assertEquals(2, word.getRating(),
            "Expected rating to be calculated correctly when add rating is called two times");
    }

    @Test
    void testAddRatingThreeTimesSameRating() {
        word = new SentimentWord("word", 0, 0);

        word.addRating(3);
        word.addRating(3);
        word.addRating(3);
        assertEquals(3, word.getRating(),
            "Expected rating to be calculated correctly when add rating is called three times");
    }

    @Test
    void testAddRatingThreeTimesResultThreeInPeriod() {
        word = new SentimentWord("word", 0, 0);

        word.addRating(1);
        word.addRating(0);
        word.addRating(0);
        assertEquals(0.333, word.getRating(), 3,
            "Expected rating to be correct when it has a period of 3 after function is called 3 times");
    }


    @Test
    void testAddRatingThreeTimesPeriodResultSixInPeriod() {
        word = new SentimentWord("word", 0, 0);

        word.addRating(2);
        word.addRating(0);
        word.addRating(0);

        assertEquals(0.666, word.getRating(), 3,
            "Expected rating to be correct when it has a period of 6 after function is called 3 times");
    }

    @Test
    void testAddRatingPeriodResultInfiniteDouble() {
        word = new SentimentWord("word", 3, 6);

        word.addRating(4);
        assertEquals(3.142, word.getRating(), 3,
            "Expected rating to be calculated correctly when it is an infinite fraction");
    }

    @Test
    void testAddRatingThreeTimesDoubleResult() {
        word = new SentimentWord("word", 0, 1);

        word.addRating(3);
        word.addRating(1);
        word.addRating(1);
        assertEquals(1.25, word.getRating(), 3,
            "Expected rating to be correct when it is a double number after function is called 3 times");
    }
}