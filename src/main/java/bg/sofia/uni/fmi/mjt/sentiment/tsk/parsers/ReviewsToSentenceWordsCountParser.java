package bg.sofia.uni.fmi.mjt.sentiment.tsk.parsers;

import bg.sofia.uni.fmi.mjt.sentiment.tsk.SentimentAnalyzer;
import bg.sofia.uni.fmi.mjt.sentiment.tsk.parsers.review.ReviewTextToSentenceWordsParser;
import bg.sofia.uni.fmi.mjt.sentiment.tsk.review.Review;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReviewsToSentenceWordsCountParser {

    // The SentimentAnalyzer function getMostFrequentWords() may
    // just count from the review list as stream every time it is called. (saving space for time)
    // (or)
    // The SentimentWord object may contain a field total count in reviews
    // but this will lead to more complex implementation.
    //
    // The downsides of the map as a counter is that will have to work with and update two collections
    // that give similar information.
    // And also the program will have to apply multiple transformations that have already been applied
    // to the list of reviews (as stream), so we are sacrificing time and efficiency.

    public static Map<String, Long> getSentenceWordsCount(List<Review> reviews, SentimentAnalyzer analyzer) {

        return reviews.stream()
            .map(Review::text)
            .map(s -> ReviewTextToSentenceWordsParser.getSentenceWords(s, analyzer))
            .flatMap(List::stream)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
