package bg.sofia.uni.fmi.mjt.sentiment.review;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReviewTest {
    @Test
    void testIsReviewLineIsNull() {
        assertFalse(Review.isReview(null),
            "Null is not a review");
    }

    @Test
    void testIsReviewLineIsEmpty() {
        assertFalse(Review.isReview(""),
            "Empty line is not a review");
    }

    @Test
    void testIsReviewLineIsBlank() {
        assertFalse(Review.isReview("  "),
            "Blank line is not a review");
    }

    @Test
    void testIsReviewLineIsReview() {
        assertTrue(Review.isReview("5 I am a review"),
            "Expected line to be a correct review");
    }

    @Test
    void testIsReviewLineIsReviewLineSeparatorAtEnd() {
        assertFalse(Review.isReview("3 I am a review" + System.lineSeparator()),
            "Expected line not to be a correct review when there is a separator at the end");
    }

    @Test
    void testIsReviewScoreHasNoSpace() {
        assertFalse(Review.isReview("4I NOT am a review"),
            "Expected line not to be a correct review when there is no space after the score value");
    }

    @Test
    void testIsReviewScoreHasMultipleSpace() {
        assertTrue(Review.isReview("2    I am a review"),
            "Expected line to be a correct review when there are multiple spaces after the score value");
    }

    @Test
    void testIsReviewLineHasNoScore() {
        assertFalse(Review.isReview("I NOT am a review"),
            "Expected line not to be a correct review when score is not defined");
    }

    @Test
    void testIsReviewScoreIsMultiDigit() {
        assertTrue(Review.isReview("14 I am a review with big score"),
            "Expected line to be a correct review when score is bigger or equal to 10");
    }

    @Test
    void testIsReviewScoreHasSpaceBeforeIt() {
        assertFalse(Review.isReview("  1 I am a review"),
            "Expected line not to be a correct review when there is space before the score value");
    }

    @Test
    void testIsReviewScoreInTheMiddle() {
        assertFalse(Review.isReview("This is not a 5 score review"),
            "Expected line not to be a correct review when score is in the middle of the text");
    }

    @Test
    void testIsReviewScoreInTheMiddleWithLetters() {
        assertFalse(Review.isReview("This is not a5 score review"),
            "Expected line not to be a correct review when score is in the middle of the text");
    }

    @Test
    void testIsReviewScoreInTheEnd() {
        assertFalse(Review.isReview("This is not review 1"),
            "Expected line not to be a correct review when score is in the end of the text");
    }

    @Test
    void testIsReviewScoreInTheEndWithSpaces() {
        assertFalse(Review.isReview("This is not review 1   "),
            "Expected line not to be a correct review when score is in the end of the text");
    }

    @Test
    void testIsReviewLineIsOnlyNumbers() {
        assertTrue(Review.isReview("1 01101111 01101011"),
            "Expected line to be a correct review when there are only numbers");
    }

    @Test
    void testIsReviewLineHasSymbols() {
        assertTrue(Review.isReview("4 I am #happy to be @ble to write to Mr-Collins about *** (stars)! Thanks!"),
            "Expected line to be a correct review when there are symbols");
    }

    @Test
    void testIsReviewScoreIsNegative() {
        assertFalse(Review.isReview("-5 I am not a review"),
            "Expected line not to be a correct review when score is negative");
    }

    @Test
    void testIsReviewScoreIsZero() {
        assertTrue(Review.isReview("0 I am a review"),
            "Expected line to be a correct review when score is 0");
    }

    @Test
    void testIsReviewScoreIsDoubleWithPoint() {
        assertFalse(Review.isReview("1.4 I am a review"),
            "Expected line not to be a correct review when score is a double");
    }

    @Test
    void testIsReviewScoreIsDoubleWithComma() {
        assertFalse(Review.isReview("1,4 I am a review"),
            "Expected line not to be a correct review when score is a double");
    }

    @Test
    void testIsReviewLineIsOnlyScoreDigit() {
        assertFalse(Review.isReview("1"),
            "Expected line not to be a correct review when line is only score digit without space");
    }

    @Test
    void testIsReviewLineIsOnlyScoreWithOneSpace() {
        assertFalse(Review.isReview("1 "),
            "Expected line not to be a correct review when line is only score digit with one space");
    }

    @Test
    void testIsReviewLineIsOnlyScoreWithMultipleSpace() {
        assertTrue(Review.isReview("1    "),
            "Expected line not to be a correct review when line is only score digit with multiple space");
    }
}
