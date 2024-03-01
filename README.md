# Movie-Review-Sentiment-Analyzer

This project implements a sentiment analyzer for movie reviews using Machine Learning techniques. The sentiment analyzer is trained on a dataset of 8,528 movie reviews sourced from Rotten Tomatoes. Each review is associated with a rating, which corresponds to a sentiment (negative, somewhat negative, neutral, somewhat positive, positive).

## Features

- Sentiment scoring for movie reviews.
- Analysis based on word sentiments.
- Handling of stopwords for noise reduction.
- Dataset expansion for continuous learning.

## Usage:

```java
// Initialize the sentiment analyzer
MovieReviewSentimentAnalyzer analyzer = new MovieReviewSentimentAnalyzer(stopwordsReader, reviewsReader, reviewsWriter);

// Get sentiment score of a review
double sentimentScore = analyzer.getReviewSentiment("The movie was fantastic!");

// Get sentiment score of a word
double wordSentiment = analyzer.getWordSentiment("fantastic");

// Get frequency of a word in the dataset
int wordFrequency = analyzer.getWordFrequency("fantastic");

// Get most frequent words
List<String> frequentWords = analyzer.getMostFrequentWords(10);

// Get most positive words
List<String> positiveWords = analyzer.getMostPositiveWords(10);

// Get most negative words
List<String> negativeWords = analyzer.getMostNegativeWords(10);

// Append a new review to the dataset
boolean appended = analyzer.appendReview("Great movie!", 4);
```

## Build
To build and run the project, clone the repository and import it into your Java IDE. Ensure that the required dependencies are included.

## Testing
Automated tests are included to validate the functionality of the sentiment analyzer. To run the tests, execute the test suite provided.