package bg.sofia.uni.fmi.mjt.sentiment.tsk.words;

import java.util.Objects;

public class SentimentWord {
    private final String text;

    private double rating;

    private int countOfRatings;

    private int frequency;

    public SentimentWord(String text, double rating, int countOfRatings) {
        this.text = text;
        this.rating = rating;
        this.countOfRatings = countOfRatings;
        this.frequency = 0;
    }

    public void addRating(double addedRating) {
        rating = (countOfRatings * rating + addedRating) / (++countOfRatings);
    }

    public String getText() {
        return text;
    }

    public double getRating() {
        return rating;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void addFrequency(int frequencyToAdd) {
        this.frequency += frequencyToAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentimentWord that = (SentimentWord) o;
        return Double.compare(that.rating, rating) == 0 && countOfRatings == that.countOfRatings &&
            text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, rating, countOfRatings);
    }
}
