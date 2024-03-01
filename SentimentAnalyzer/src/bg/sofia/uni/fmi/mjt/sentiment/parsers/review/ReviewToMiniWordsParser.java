package bg.sofia.uni.fmi.mjt.sentiment.parsers.review;

import bg.sofia.uni.fmi.mjt.sentiment.SentimentAnalyzer;
import bg.sofia.uni.fmi.mjt.sentiment.review.Review;
import bg.sofia.uni.fmi.mjt.sentiment.words.MiniWord;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReviewToMiniWordsParser {
    public static Set<MiniWord> getMiniWords(Review review, SentimentAnalyzer analyzer) {

        List<String> listOfWordsAsText = ReviewTextToSentenceWordsParser.getSentenceWords(review.text(), analyzer);

        return listOfWordsAsText.stream()
            .map(s -> new MiniWord(review.score(), s))
            .collect(Collectors.toSet());
    }
}
