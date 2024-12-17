package org.example.analyzer;

import java.util.*;

public class TextAnalyzer {
    public static Map<String, Integer> analyzeText(String text) {
        Map<String, Integer> wordCount = new HashMap<>();
        DictionaryProvider.getDictionaries().keySet().forEach(key -> wordCount.put(key, 0));

        String[] words = text.toLowerCase().replaceAll("[^а-яё\\s]", "").split("\\s+");
        for (String word : words) {
            String stemmedWord = StemmingService.stem(word);
            DictionaryProvider.getDictionaries().forEach((key, dictionary) -> {
                if (dictionary.contains(stemmedWord)) {
                    wordCount.put(key, wordCount.get(key) + 1);
                }
            });
        }
        return wordCount;
    }
}
