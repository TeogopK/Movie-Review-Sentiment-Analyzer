# Movie-Review-Sentiment-Analyzer

This project implements a sentiment analyzer for movie reviews using Machine Learning techniques. The sentiment analyzer is trained on a dataset of 8,528 movie reviews sourced from Rotten Tomatoes. Each review is associated with a rating, which corresponds to a sentiment (negative, somewhat negative, neutral, somewhat positive, positive).

## Features

- Sentiment scoring for movie reviews.
- Analysis based on word sentiments.
- Handling of stopwords for noise reduction.
- Dataset expansion for continuous learning.
  
## Training

1. Reads the reviews from the provided *reviewsIn Reader* (e.g. the *movieReviews.txt* file in Resources).
2. Calculates the sentiment score for each word as the average rating it appears in.
3. Ignores stopwords from the provided *stopwordsIn Reader* (e.g. the *stopwords.txt* file in Resources) and non-word characters during analysis.

## Recognition

1. Computes the sentiment score for a given review as the average sentiment score of the words in the review.
2. Ignores stopwords and unknown words during analysis.
3. If a review contains only unknown words or stopwords, the sentiment score is considered unknown (-1.0).

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

To build and run the project, ensure you have Maven installed on your system. Clone the repository to your local machine, navigate to the project folder and build the project using:

```bash
mvn package
```


## Testing

Automated tests are included to validate the functionality of the sentiment analyzer. To run the tests execute:

```bash
mvn test
```
