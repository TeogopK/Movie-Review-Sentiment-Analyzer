package bg.sofia.uni.fmi.mjt.sentiment;

public enum SentimentType {
    UNKNOWN, NEGATIVE, SOMEWHAT_NEGATIVE, NEUTRAL, SOMEWHAT_POSITIVE, POSITIVE;

    private static final SentimentType[] VALUES = SentimentType.values();

    public static final int UNKNOWN_VALUE_NUMBER = -1;

    public static SentimentType getSentimentType(int i) {
        if (i == UNKNOWN_VALUE_NUMBER) {
            return VALUES[0];
        }
        return VALUES[i + 1];
    }

    public static String getAsString(SentimentType sentimentType) {
        return switch (sentimentType) {
            case NEGATIVE -> "negative";
            case SOMEWHAT_NEGATIVE -> "somewhat negative";
            case NEUTRAL -> "neutral";
            case SOMEWHAT_POSITIVE -> "somewhat positive";
            case POSITIVE -> "positive";
            case UNKNOWN -> "unknown";
        };
    }
}
