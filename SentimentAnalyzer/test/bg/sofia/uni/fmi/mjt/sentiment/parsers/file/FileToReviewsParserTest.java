package bg.sofia.uni.fmi.mjt.sentiment.parsers.file;

import bg.sofia.uni.fmi.mjt.sentiment.review.Review;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class FileToReviewsParserTest {

    @Test
    void testGetReviewsIsReviewOneLine() {
        String input = "4 This is a review!" + System.lineSeparator();
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Review review = new Review(4, "This is a review!");
        List<Review> list = List.of(review);

        assertIterableEquals(list, FileToReviewsParser.getReviews(reader),
            "Expected reviews to be parsed correctly when there is only one review");
    }

    @Test
    void testGetReviewsOneLineNoLineSeparator() {
        String input = "4 This is a review!";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Review review = new Review(4, "This is a review!");
        List<Review> list = List.of(review);

        assertIterableEquals(list, FileToReviewsParser.getReviews(reader),
            "Expected reviews to be parsed correctly when there is only one review with no line separator");
    }

    @Test
    void testGetReviewsOneLineSpacesAtBeginning() {
        String input = "   4 This is a review!";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Review review = new Review(4, "This is a review!");
        List<Review> list = List.of(review);

        assertIterableEquals(list, FileToReviewsParser.getReviews(reader),
            "Expected reviews to be parsed correctly when there is spaces before the score");
    }

    @Test
    void testGetReviewsOneLineSpacesAfterScore() {
        String input = "   4  This is a spaced review!";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        //only one space split
        Review review = new Review(4, " This is a spaced review!");
        List<Review> list = List.of(review);

        assertIterableEquals(list, FileToReviewsParser.getReviews(reader),
            "Expected reviews to be parsed correctly when there is spaces after the score");
    }

    @Test
    void testGetReviewsOneLineNoSpacesAfterScore() {
        String input = "   4This is a bad review!";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertIterableEquals(Collections.emptyList(), FileToReviewsParser.getReviews(reader),
            "Expected no reviews to be parsed correctly when score has no space after it");
    }

    @Test
    void testGetReviewsAllCaps() {
        String input = "1 ALL CAPS";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Review review2 = new Review(1, "ALL CAPS");
        List<Review> list2 = List.of(review2);

        assertIterableEquals(list2, FileToReviewsParser.getReviews(reader),
            "Expected reviews to be parsed correctly input is all caps");
    }

    @Test
    void testGetReviewsSymbols() {
        String input = "1 <-Definitely @ review 4 me! #bonus";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Review review = new Review(1, "<-Definitely @ review 4 me! #bonus");
        List<Review> list = List.of(review);

        assertIterableEquals(list, FileToReviewsParser.getReviews(reader),
            "Expected reviews to be parsed correctly input is all caps");
    }

    @Test
    void testGetReviewsOneLineNoReviews() {
        String input = "I am not a review";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertIterableEquals(Collections.emptyList(), FileToReviewsParser.getReviews(reader),
            "Expected review not to be parsed when there are no reviews");
    }

    @Test
    void testGetReviewsMultipleLinesNoReviews() {
        String input = "I am not a review" + System.lineSeparator() + "Sorry 4 you I am also not a review";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertIterableEquals(Collections.emptyList(), FileToReviewsParser.getReviews(reader),
            "Expected review not to be parsed when there are no reviews");
    }

    @Test
    void testGetReviewsOnlyScore() {
        String input = "4 ";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertIterableEquals(Collections.emptyList(), FileToReviewsParser.getReviews(reader),
            "Expected review not to be parsed when there is only score");
    }

    @Test
    void testGetReviewsOneLineEmpty() {
        String input = "";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertIterableEquals(Collections.emptyList(), FileToReviewsParser.getReviews(reader),
            "Expected no reviews to be parsed when line is empty");
    }

    @Test
    void testGetReviewsOneLineBlank() {
        String input = " ";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertIterableEquals(Collections.emptyList(), FileToReviewsParser.getReviews(reader),
            "Expected no reviews to be parsed when line is blank");
    }

    @Test
    void testGetReviewsLineSeparatorsOnly() {
        String input = System.lineSeparator() + System.lineSeparator() + System.lineSeparator();
        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertIterableEquals(Collections.emptyList(), FileToReviewsParser.getReviews(reader),
            "Expected no reviews to be parsed when input is only line separators");
    }

    @Test
    void testGetReviewsThreeOfThree() {
        String input =
            "1 I am the first review!" + System.lineSeparator() + "2 I am the second review!" + System.lineSeparator() +
                "3 I am the third review!" + System.lineSeparator();

        BufferedReader reader = new BufferedReader(new StringReader(input));

        Review review1 = new Review(1, "I am the first review!");
        Review review2 = new Review(2, "I am the second review!");
        Review review3 = new Review(3, "I am the third review!");
        List<Review> list = List.of(review1, review2, review3);

        assertIterableEquals(list, FileToReviewsParser.getReviews(reader),
            "Expected all three lines to be parsed correctly");
    }

    @Test
    void testGetReviewsTwoOfThree() {
        String input = "1 I am the first review!" + System.lineSeparator() + "I am not the second review!" +
            System.lineSeparator() + "2 I am the second review!" + System.lineSeparator();

        BufferedReader reader = new BufferedReader(new StringReader(input));

        Review review1 = new Review(1, "I am the first review!");
        Review review2 = new Review(2, "I am the second review!");
        List<Review> list = List.of(review1, review2);

        assertIterableEquals(list, FileToReviewsParser.getReviews(reader),
            "Expected only two out of three lines to be parsed correctly");
    }

    @Test
    void testGetReviewsZeroOfThree() {
        String input = "I am not the first review!" + System.lineSeparator() + "I am not the second review! 4" +
            System.lineSeparator() + "   " + System.lineSeparator();

        BufferedReader reader = new BufferedReader(new StringReader(input));

        assertIterableEquals(Collections.emptyList(), FileToReviewsParser.getReviews(reader),
            "Expected no reviews to be parsed");
    }

    @Test
    void testGetReviewsDuplicates() {
        String input =
            "1 I am the first review!" + System.lineSeparator() + "2 I am the second review!" + System.lineSeparator() +
                "1 I am the first review!" + System.lineSeparator();

        BufferedReader reader = new BufferedReader(new StringReader(input));

        Review review1 = new Review(1, "I am the first review!");
        Review review2 = new Review(2, "I am the second review!");
        Review review3 = new Review(1, "I am the first review!");
        List<Review> list = List.of(review1, review2, review3);

        assertIterableEquals(list, FileToReviewsParser.getReviews(reader),
            "Expected all three lines to be parsed correctly when there is duplicates");

        assertEquals(3, list.size(), "Expected size of reviews to be 3 (of 3)when there are duplicates");
    }

    @Test
    void testGetReviewsMultipleLineSeparators() {
        String input = System.lineSeparator() + System.lineSeparator() + "1 Here I am" + System.lineSeparator() +
            System.lineSeparator();

        BufferedReader reader = new BufferedReader(new StringReader(input));

        Review review = new Review(1, "Here I am");
        List<Review> list = List.of(review);

        assertIterableEquals(list, FileToReviewsParser.getReviews(reader),
            "Expected one review to be parsed between multiple line separators");
    }
}
