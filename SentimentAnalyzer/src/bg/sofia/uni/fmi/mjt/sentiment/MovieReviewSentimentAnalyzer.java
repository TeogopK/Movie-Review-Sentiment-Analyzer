package bg.sofia.uni.fmi.mjt.sentiment;

import bg.sofia.uni.fmi.mjt.sentiment.parsers.ReviewsToSentenceWordsCountParser;
import bg.sofia.uni.fmi.mjt.sentiment.parsers.ReviewsToSentimentWordsParser;
import bg.sofia.uni.fmi.mjt.sentiment.parsers.file.FileToReviewsParser;
import bg.sofia.uni.fmi.mjt.sentiment.parsers.file.FileToStopwordsParser;
import bg.sofia.uni.fmi.mjt.sentiment.parsers.review.ReviewTextToSentenceWordsParser;
import bg.sofia.uni.fmi.mjt.sentiment.parsers.review.ReviewToMiniWordsParser;
import bg.sofia.uni.fmi.mjt.sentiment.review.Review;
import bg.sofia.uni.fmi.mjt.sentiment.words.SentimentWord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {

    private List<Review> reviews;

    private Map<String, SentimentWord> dictionary;

    private Set<String> stopwords;

    private Writer reviewsOut;

    final private static double SENTIMENT_LOWER_BOUND = 0;
    final private static double SENTIMENT_UPPER_BOUND = 4;

    public MovieReviewSentimentAnalyzer(Reader stopwordsIn, Reader reviewsIn, Writer reviewsOut) {
        initReview(reviewsIn);
        initStopwords(stopwordsIn);

        initDictionary();

        this.reviewsOut = reviewsOut;
    }

    private void initReview(Reader reviewsIn) {
        reviews = FileToReviewsParser.getReviews(new BufferedReader(reviewsIn));
    }

    private void initStopwords(Reader reviewsIn) {
        stopwords = FileToStopwordsParser.getStopwords(new BufferedReader(reviewsIn));
    }

    private void initDictionary() {
        Map<String, Long> frequencies = getWordFrequencyMap();
        Set<SentimentWord> wordsSet = ReviewsToSentimentWordsParser.getSentimentWords(reviews, this);

        setFrequencies(frequencies, wordsSet);

        dictionary = wordsSet.stream().collect(Collectors.toMap(SentimentWord::getText, w -> w));
    }

    private Map<String, Long> getWordFrequencyMap() {
        return ReviewsToSentenceWordsCountParser.getSentenceWordsCount(reviews, this);
    }

    private void setFrequencies(Map<String, Long> frequencies, Set<SentimentWord> wordsSet) {
        wordsSet.forEach(w -> w.setFrequency(
            frequencies.get(
                w.getText()
            ).intValue())
        );
    }

    @Override
    public double getReviewSentiment(String review) {
        return ReviewTextToSentenceWordsParser.getSentenceWords(review, this)
            .stream()
            .filter(dictionary::containsKey)
            .flatMapToDouble(w -> DoubleStream.of(dictionary.get(w).getRating()))
            .average()
            .orElse(-1);
    }

    @Override
    public String getReviewSentimentAsName(String review) {
        int exactRating = (int) Math.round(getReviewSentiment(review));

        return SentimentType.getAsString(SentimentType.getSentimentType(exactRating));
    }

    @Override
    public double getWordSentiment(String word) {
        if (!dictionary.containsKey(word.toLowerCase())) {
            return -1;
        }
        return dictionary.get(word.toLowerCase()).getRating();
    }

    @Override
    public int getWordFrequency(String word) {
        if (!dictionary.containsKey(word.toLowerCase())) {
            return 0;
        }
        return dictionary.get(word.toLowerCase()).getFrequency();
    }

    @Override
    public List<String> getMostFrequentWords(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n can not be negative");
        }

        return dictionary.values().stream()
            .sorted(Comparator.comparing(SentimentWord::getFrequency).reversed().thenComparing(SentimentWord::getText))
            .map(SentimentWord::getText)
            .limit(n)
            .collect(Collectors.toList());
    }

    @Override
    public List<String> getMostPositiveWords(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n can not be negative");
        }

        return dictionary.values().stream()
            .sorted(Comparator.comparing(SentimentWord::getRating).reversed().thenComparing(SentimentWord::getText))
            .map(SentimentWord::getText)
            .limit(n)
            .collect(Collectors.toList());
    }

    @Override
    public List<String> getMostNegativeWords(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n can not be negative");
        }

        return dictionary.values().stream()
            .sorted(Comparator.comparing(SentimentWord::getRating).thenComparing(SentimentWord::getText))
            .map(SentimentWord::getText)
            .limit(n)
            .collect(Collectors.toList());
    }

    @Override
    public boolean appendReview(String review, int sentiment) {
        if (review == null || review.isEmpty() || review.isBlank()) {
            throw new IllegalArgumentException("Review can not be null, empty or blank");
        }
        if (sentiment < SENTIMENT_LOWER_BOUND || sentiment > SENTIMENT_UPPER_BOUND) {
            throw new IllegalArgumentException("Sentiment should not be outside the defined bounds");
        }

        // This behaviour is not defined in the doc
        if (review.contains(System.lineSeparator())) {
            throw new IllegalArgumentException("Review can not contain line separator");
        }

        Review reviewToAdd = new Review(sentiment, review);

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(reviewsOut);

            bufferedWriter.append(reviewToAdd.getStringToSerialize());
            bufferedWriter.flush();
        } catch (IOException e) {
            return false;
        }

        reviews.add(reviewToAdd);

        updateSentimentScoreExisting(reviewToAdd);
        updateSentimentScoreCreateNew(reviewToAdd);

        updateFrequency(reviewToAdd);

        return true;
    }

    private void updateSentimentScoreExisting(Review newReview) {
        ReviewToMiniWordsParser.getMiniWords(newReview, this).stream()
            .filter(m -> dictionary.containsKey(m.text()))
            .forEach(w -> dictionary.get(w.text()).addRating(w.score()));
    }

    private void updateSentimentScoreCreateNew(Review newReview) {
        ReviewsToSentimentWordsParser.getSentimentWords(List.of(newReview), this)
            .stream()
            .filter(Predicate.not(m -> dictionary.containsKey(m.getText())))
            .forEach(w -> dictionary.put(w.getText(), w));
    }

    private void updateFrequency(Review newReview) {
        ReviewsToSentenceWordsCountParser.getSentenceWordsCount(List.of(newReview), this)
            .forEach((key, value) -> dictionary.get(key).addFrequency(value.intValue()));
    }

    @Override
    public int getSentimentDictionarySize() {
        return dictionary.size();
    }

    @Override
    public boolean isStopWord(String word) {
        return stopwords.contains(word.toLowerCase());
    }
}
