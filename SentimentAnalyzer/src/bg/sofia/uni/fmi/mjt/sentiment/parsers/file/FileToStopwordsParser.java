package bg.sofia.uni.fmi.mjt.sentiment.parsers.file;

import java.io.BufferedReader;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileToStopwordsParser {
    public static Set<String> getStopwords(BufferedReader bufferedReader) {

        return bufferedReader.lines().map(String::trim)
            .filter(Predicate.not(String::isEmpty))
            .map(String::toLowerCase)
            .collect(Collectors.toSet());
    }
}
