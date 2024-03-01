package bg.sofia.uni.fmi.mjt.sentiment.parsers.file;

import bg.sofia.uni.fmi.mjt.sentiment.review.Review;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;

public class FileToReviewsParser {

    public static List<Review> getReviews(BufferedReader bufferedReader) {
        return bufferedReader.lines()
            .map(String::strip)
            .filter(Review::isReview)
            .map(Review::of)
            .collect(Collectors.toList());
    }
}
