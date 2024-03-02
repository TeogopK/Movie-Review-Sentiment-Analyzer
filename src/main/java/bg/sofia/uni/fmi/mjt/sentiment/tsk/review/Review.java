package bg.sofia.uni.fmi.mjt.sentiment.tsk.review;

import java.util.regex.Pattern;

// this may as well be a class
public record Review(int score, String text) {
    private static final String REVIEW_ATTRIBUTE_DELIMITER = " ";
    private static final int ATTRIBUTES_COUNT = 2;
    private static final Pattern PATTERN_HAS_NUMBER_AND_SPACE_AT_THE_BEGINNING = Pattern.compile("^\\d+( ).+");

    public static Review of(String line) {
        final String[] tokens = line.split(REVIEW_ATTRIBUTE_DELIMITER, ATTRIBUTES_COUNT);

        return new Review(Integer.parseInt(tokens[0]), tokens[1]);
    }

    public static boolean isReview(String line) {
        if (line == null) {
            return false;
        }
        return PATTERN_HAS_NUMBER_AND_SPACE_AT_THE_BEGINNING.matcher(line).matches();
    }

    public String getStringToSerialize() {
        return score + REVIEW_ATTRIBUTE_DELIMITER + text + System.lineSeparator();
    }
}
