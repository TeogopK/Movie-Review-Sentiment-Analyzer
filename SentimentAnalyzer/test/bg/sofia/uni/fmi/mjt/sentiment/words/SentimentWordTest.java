package bg.sofia.uni.fmi.mjt.sentiment.words;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SentimentWordTest {
    private SentimentWord word;

    @Test
    void testAddRatingCurrentAndAddedAreIntegersResultSomewhatPositive() {
        word = new SentimentWord("word", 4, 3);

        word.addRating(0);
        assertEquals(3, word.getRating(),
            "Expected rating to be calculated correctly when both are integers");
    }

    @Test
    void testAddRatingCurrentAndAddedAreIntegersResultPositive() {
        word = new SentimentWord("word", 4, 3);

        word.addRating(4);
        assertEquals(4, word.getRating(),
            "Expected rating to be calculated correctly when both are integers");
    }

    @Test
    void testAddRatingCurrentAndAddedAreIntegersResultNeutral() {
        word = new SentimentWord("word", 2, 1);

        word.addRating(2);
        assertEquals(2, word.getRating(),
            "Expected rating to be calculated correctly when both are integers");
    }

    @Test
    void testAddRatingCurrentAndAddedAreIntegersResultNegative() {
        word = new SentimentWord("word", 0, 10);

        word.addRating(0);
        assertEquals(0, word.getRating(),
            "Expected rating to be calculated correctly when both are integers");
    }

    @Test
    void testAddRatingCurrentAndAddedAreIntegersResultSomewhatNegative() {
        word = new SentimentWord("word", 0, 2);

        word.addRating(3);
        assertEquals(1, word.getRating(),
            "Expected rating to be calculated correctly when both are integers");
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

//    @Test
//    void testGetSentimentTypeAsStringRatingIsZero() {
//        word = new SentimentWord("word", 0, 1);
//
//        assertEquals("negative", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be negative when rating is 0");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsBelowNegativeUpperBound() {
//        word = new SentimentWord("word", 0.4, 1);
//
//        assertEquals("negative", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be negative when rating is 0.4");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsJustUnderNegativeUpperBound() {
//        word = new SentimentWord("word", 0.49999, 1);
//
//        assertEquals("negative", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be negative when rating is 0.49999");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsNegativeUpperBound() {
//        word = new SentimentWord("word", 0.5, 1);
//
//        assertEquals("somewhat negative", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat negative when rating is 0.5");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsJustAboveNegativeUpperBound() {
//        word = new SentimentWord("word", 0.501, 1);
//
//        assertEquals("somewhat negative", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat negative when rating is 0.501");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsAboveNegativeUpperBound() {
//        word = new SentimentWord("word", 0.768, 1);
//
//        assertEquals("somewhat negative", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat negative when rating is 0.768");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsOne() {
//        word = new SentimentWord("word", 1, 1);
//
//        assertEquals("somewhat negative", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat negative when rating is 1");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsBelowSomewhatNegativeUpperBound() {
//        word = new SentimentWord("word", 1.4, 1);
//
//        assertEquals("somewhat negative", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat negative when rating is 1.4");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsJustUnderSomewhatNegativeUpperBound() {
//        word = new SentimentWord("word", 1.49999, 1);
//
//        assertEquals("somewhat negative", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat negative when rating is 1.49999");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsSomewhatNegativeUpperBound() {
//        word = new SentimentWord("word", 1.5, 1);
//
//        assertEquals("neutral", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be neutral when rating is 1.5");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsJustAboveSomewhatNegativeUpperBound() {
//        word = new SentimentWord("word", 1.501, 1);
//
//        assertEquals("neutral", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be neutral when rating is 1.501");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsAboveSomewhatNegativeUpperBound() {
//        word = new SentimentWord("word", 1.768, 1);
//
//        assertEquals("neutral", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be neutral when rating is 1.768");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsTwo() {
//        word = new SentimentWord("word", 2, 1);
//
//        assertEquals("neutral", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be neutral when rating is 2");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsBelowNeutralUpperBound() {
//        word = new SentimentWord("word", 2.4, 1);
//
//        assertEquals("neutral", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be neutral when rating is 2.4");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsJustUnderNeutralUpperBound() {
//        word = new SentimentWord("word", 2.49999, 1);
//
//        assertEquals("neutral", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be neutral when rating is 2.49999");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsNeutralUpperBound() {
//        word = new SentimentWord("word", 2.5, 1);
//
//        assertEquals("somewhat positive", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat positive when rating is 2.5");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsJustAboveNeutralUpperBound() {
//        word = new SentimentWord("word", 2.501, 1);
//
//        assertEquals("somewhat positive", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat positive when rating is 2.501");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsAboveNeutralUpperBound() {
//        word = new SentimentWord("word", 2.768, 1);
//
//        assertEquals("somewhat positive", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat positive when rating is 2.768");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsThree() {
//        word = new SentimentWord("word", 3, 3);
//
//        assertEquals("somewhat positive", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat positive when rating is 3");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsBelowSomewhatPositiveUpperBound() {
//        word = new SentimentWord("word", 3.4, 2);
//
//        assertEquals("somewhat positive", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat positive when rating is 3.4");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsJustUnderSomewhatPositiveUpperBound() {
//        word = new SentimentWord("word", 3.49999, 9);
//
//        assertEquals("somewhat positive", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be somewhat positive when rating is 3.49999");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsSomewhatPositiveUpperBound() {
//        word = new SentimentWord("word", 3.5, 4);
//
//        assertEquals("positive", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be positive when rating is 3.5");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsJustAboveSomewhatPositiveUpperBound() {
//        word = new SentimentWord("word", 3.501, 3);
//
//        assertEquals("positive", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be positive when rating is 3.501");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsAboveSomewhatPositiveUpperBound() {
//        word = new SentimentWord("word", 3.768, 12);
//
//        assertEquals("positive", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be positive when rating is 3.768");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsFour() {
//        word = new SentimentWord("word", 4, 13);
//
//        assertEquals("positive", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be positive when rating is 4");
//    }
//
//    @Test
//    void testGetSentimentTypeAsStringRatingIsMinusOne() {
//        word = new SentimentWord("word", -1, 13);
//
//        assertEquals("unknown", word.getSentimentTypeAsString(),
//            "Expected sentiment type to be unknown when rating is -1");
//    }
}
