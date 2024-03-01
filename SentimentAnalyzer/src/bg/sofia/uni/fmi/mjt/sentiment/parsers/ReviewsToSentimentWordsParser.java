package bg.sofia.uni.fmi.mjt.sentiment.parsers;

import bg.sofia.uni.fmi.mjt.sentiment.SentimentAnalyzer;
import bg.sofia.uni.fmi.mjt.sentiment.parsers.review.ReviewToMiniWordsParser;
import bg.sofia.uni.fmi.mjt.sentiment.review.Review;
import bg.sofia.uni.fmi.mjt.sentiment.words.MiniWord;
import bg.sofia.uni.fmi.mjt.sentiment.words.SentimentWord;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReviewsToSentimentWordsParser {

    public static Set<SentimentWord> getSentimentWords(List<Review> reviews, SentimentAnalyzer analyzer) {

        var map = getMapOfMiniWords(reviews, analyzer);

        return map.entrySet().stream()
            .map(ReviewsToSentimentWordsParser::getSentimentWordFrom)
            .collect(Collectors.toSet());
    }

    private static SentimentWord getSentimentWordFrom(Map.Entry<String, List<MiniWord>> entry) {
        double sum = entry.getValue().stream()
            .flatMapToInt(mw -> IntStream.of(mw.score()))
            .sum();

        int count = entry.getValue().size();

        return new SentimentWord(entry.getKey(), sum / count, count);
    }

    private static Map<String, List<MiniWord>> getMapOfMiniWords(List<Review> reviews, SentimentAnalyzer analyzer) {

        return reviews.stream()
            .map(r -> ReviewToMiniWordsParser.getMiniWords(r, analyzer))
            .flatMap(Set::stream)
            .collect(
                Collectors.groupingBy(MiniWord::text)
            );
    }
}
