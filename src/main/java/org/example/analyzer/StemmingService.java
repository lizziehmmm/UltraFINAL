package org.example.analyzer;

import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.RussianStemmer;

import java.io.StringReader;

public class StemmingService {

    public static String stem(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        try {
            // Приведение слова к нижнему регистру вручную
            String lowerCasedWord = word.toLowerCase();

            // Создание TokenStream на основе строки
            TokenStream tokenStream = new SnowballFilter(
                    new SimpleTokenStream(lowerCasedWord), new RussianStemmer()
            );

            CharTermAttribute attribute = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();

            StringBuilder result = new StringBuilder();
            while (tokenStream.incrementToken()) {
                result.append(attribute.toString());
            }

            tokenStream.end();
            tokenStream.close();

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return word; // В случае ошибки возвращаем оригинальное слово
        }
    }

    // Вспомогательный класс для создания простого TokenStream
    private static class SimpleTokenStream extends TokenStream {
        private final CharTermAttribute charTermAttribute = addAttribute(CharTermAttribute.class);
        private boolean tokenEmitted = false;
        private final String token;

        public SimpleTokenStream(String token) {
            this.token = token;
        }

        @Override
        public boolean incrementToken() {
            if (tokenEmitted) {
                return false;
            }
            clearAttributes();
            charTermAttribute.append(token);
            tokenEmitted = true;
            return true;
        }
    }
}
